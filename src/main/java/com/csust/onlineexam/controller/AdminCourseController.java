package com.csust.onlineexam.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.csust.onlineexam.constant.Constant;
import com.csust.onlineexam.dto.Result;
import com.csust.onlineexam.entity.Course;
import com.csust.onlineexam.service.impl.CourseServiceImpl;
import com.csust.onlineexam.util.DgbSecurityUserHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author johnNick
 * @since 2020-05-21
 */
@RestController
@RequestMapping("/admin/course")
@Api(tags = "课程")
public class AdminCourseController {

    private final CourseServiceImpl courseService;

    @Autowired
    public AdminCourseController(CourseServiceImpl courseService) {
        this.courseService = courseService;
    }

    @GetMapping("course_management")
    public ModelAndView showCourseManagement() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/admin/course_management");
        return modelAndView;
    }
    @GetMapping("course_batch_add")
    public ModelAndView showCourseBatchAdd() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/admin/course_batch_add");
        return modelAndView;
    }
    @GetMapping("getAllCourses")
    public Map<String,Object> getAllCourses(@RequestParam(name = "page") Integer current,
                                                  @RequestParam Integer limit){
        QueryWrapper<Course> queryWrapper = new QueryWrapper<>();
        Page<Map<String, Object>> page = new Page<>(current, limit);
        Map<String, Object> result = new HashMap<>(2);
        result.put("code", 0);
        Page<Map<String, Object>> coursePage = courseService.getCourseInfoList(page,queryWrapper );
        result.put("data", coursePage.getRecords());
        result.put("count", coursePage.getTotal());
        return result;
    }




}
