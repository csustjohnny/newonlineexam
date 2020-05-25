package com.csust.onlineexam.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.csust.onlineexam.constant.Constant;
import com.csust.onlineexam.dto.Result;
import com.csust.onlineexam.entity.Course;
import com.csust.onlineexam.service.impl.CourseServiceImpl;
import com.csust.onlineexam.util.DgbSecurityUserHelper;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author johnNick
 * @since 2020-05-24
 */
@RestController
@RequestMapping("/course")
public class CourseController {

    private final CourseServiceImpl courseService;

    @Autowired
    public CourseController(CourseServiceImpl courseService) {
        this.courseService = courseService;
    }
    @GetMapping("getAllCourses")
    @ApiOperation("获取课程列表")
    public Map<String, Object> getAllCourses(@RequestParam Integer page,
                                             @RequestParam Integer limit) {
        QueryWrapper<Course> queryWrapper = new QueryWrapper<>();
        if(DgbSecurityUserHelper.getRoleList().contains(Constant.ROLE_TEACHER)){
            //教师获取自己的课程
            queryWrapper.eq("course_teacher",DgbSecurityUserHelper.getCurrentUser().getUsername().split(" ")[1]);
        }
        Map<String, Object> result = new HashMap<>(10);
        Page<Map<String, Object>> coursesPage = courseService.getCourseInfoList(new Page<>(page, limit), queryWrapper);
        result.put("code", 0);
        result.put("data", coursesPage.getRecords());
        result.put("count", coursesPage.getTotal());
        return result;
    }

}
