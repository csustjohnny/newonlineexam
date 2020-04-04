package com.csust.onlineexam.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.csust.onlineexam.entity.Result;
import com.csust.onlineexam.entity.ResultCode;
import com.csust.onlineexam.entity.Teacher;
import com.csust.onlineexam.service.impl.TeacherServiceImpl;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ：Lenovo
 * @date ：Created in 2020/3/31 18:59
 * @description : 管理员管理教师相关接口
 * @modified By：
 */
@RestController
@RequestMapping("/admin/teacher")
public class AdminTeacherController {
    private final TeacherServiceImpl teacherService;

    public AdminTeacherController(TeacherServiceImpl teacherService) {
        this.teacherService = teacherService;
    }

    @GetMapping("getAllTeachers")
    @ApiOperation("获取所有教师信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "当前页数", defaultValue = "1",required = true),
            @ApiImplicitParam(name = "limit", value = "每页数量大小", defaultValue = "10",required = true),
            @ApiImplicitParam(name = "teacherName", value = "姓名")
    })
    public Map<String,Object> getAllTeachers(@RequestParam(value = "page", defaultValue = "1") Integer pageNum,
                                             @RequestParam(value = "limit", defaultValue = "10") Integer size,
                                             @RequestParam(value = "teacherName",required = false) String teacherName) {
        QueryWrapper<Teacher> queryWrapper = new QueryWrapper<>();
        Map<String,Object> result = new HashMap<>(5);
        if(teacherName!=null && !"".equals(teacherName)){
            queryWrapper.like("teacher_name",teacherName);
        }
        Page<Teacher> page = new Page<>(pageNum, size);
        Page<Teacher> teacherPage = teacherService.page(page,queryWrapper);
        result.put("code",0);
        result.put("data",teacherPage.getRecords());
        result.put("count",teacherPage.getTotal());
        return result;
    }
    @PostMapping("/deleteOneTeacher")
    @ApiOperation("删除单个教师")
    @ApiImplicitParam(name = "teacherNo",value = "教师号",required = true)
    public Result deleteOneTeacher(@RequestParam(name = "teacherNo") String teacherNo){
        return teacherService.removeById(teacherNo)?Result.success():Result.failure(ResultCode.DELETE_FAILURE);
    }
    @PostMapping("saveOrUpdateOneTeacher")
    @ApiOperation("添加或更新单个教师信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "teacher", value = "教师信息",required = true),
            @ApiImplicitParam(name = "type", value = "类型(0添加，1更新)",required = true),
    })
    public Result saveOrUpdateTeacher(@RequestBody Teacher teacher,
                                      @RequestParam int type){
        if(type==0 && teacherService.getById(teacher.getTeacherNo()) != null){
            return Result.failure(ResultCode.INSERT_DUPLICATE);
        }
        if (teacher.getPassword()==null || "".equals(teacher.getPassword())){
            teacher.setPassword(new BCryptPasswordEncoder().encode(teacher.getTeacherNo()));
        } else {
            System.out.println(teacher.getPassword());
            teacher.setPassword(new BCryptPasswordEncoder().encode(teacher.getPassword()));
        }
        try{
            teacherService.saveOrUpdate(teacher);
        }catch(Exception e){
            e.printStackTrace();
            return Result.failure(ResultCode.INSERT_FAILURE);
        }
        return Result.success();
    }
    @PostMapping("/deleteBatchTeachers")
    @ApiOperation("批量删除教师")
    @ApiImplicitParam(name = "teacherList",value = "要删除的教师列表")
    @Transactional(rollbackFor = Exception.class)
    public Result deleteBatchTeachers(@RequestBody List<Teacher> teacherList){
        if(teacherList.size()==0){
            return Result.failure(ResultCode.PARAMS_INCORRECT);
        }
        List<String> teacherNos = new ArrayList<>();
        teacherList.forEach(teacher -> teacherNos.add(teacher.getTeacherNo()));
        teacherService.removeByIds(teacherNos);
        return Result.success();
    }
}
