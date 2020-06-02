package com.csust.onlineexam.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.csust.onlineexam.constant.Constant;
import com.csust.onlineexam.dto.Result;
import com.csust.onlineexam.entity.Course;
import com.csust.onlineexam.entity.Teacher;
import com.csust.onlineexam.service.impl.CourseServiceImpl;
import com.csust.onlineexam.service.impl.TeacherServiceImpl;
import com.csust.onlineexam.util.DgbSecurityUserHelper;
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
 * @since 2020-03-28
 */
@RestController
@RequestMapping("/teacher")
public class TeacherController {

    private final TeacherServiceImpl teacherService;
    private final CourseServiceImpl courseService;

    @Autowired
    public TeacherController(TeacherServiceImpl teacherService, CourseServiceImpl courseService) {
        this.teacherService = teacherService;
        this.courseService = courseService;
    }


    @GetMapping("/index")
    public ModelAndView getIndex() {
        ModelAndView teacher = new ModelAndView();
        teacher.setViewName("teacher");
        return teacher;
    }

    @GetMapping("/welcome")
    public ModelAndView welcome() {
        ModelAndView teacher = new ModelAndView();
        teacher.setViewName("welcomeAdmin");
        return teacher;
    }

    @GetMapping("/person_info")
    public ModelAndView showPersonInfo() {
        ModelAndView personInfo = new ModelAndView();
        personInfo.setViewName("teacher/personal_information");
        return personInfo;
    }

    @GetMapping("/showChoiceQuestionManagement")
    public ModelAndView showChoiceQuestionManagement() {
        ModelAndView personInfo = new ModelAndView();
        personInfo.setViewName("teacher/question/choiceQuestion_management");
        return personInfo;
    }


    @GetMapping("/judgementQuestion_management")
    public ModelAndView showJudgementQuestionManagement() {
        ModelAndView personInfo = new ModelAndView();
        personInfo.setViewName("teacher/question/judgementQuestion_management");
        return personInfo;
    }

    @GetMapping("/fillingBlankQuestion_management")
    public ModelAndView showFillingBlankQuestionManagement() {
        ModelAndView personInfo = new ModelAndView();
        personInfo.setViewName("teacher/question/fillingBlankQuestion_management");
        return personInfo;
    }

    @GetMapping("/codeQuestion_management")
    public ModelAndView showCodeQuestionManagement() {
        ModelAndView personInfo = new ModelAndView();
        personInfo.setViewName("teacher/question/codeQuestion_management");
        return personInfo;
    }

    @GetMapping("getPersonInfo")
    @ApiOperation("获取当前用户信息")
    public Result getPersonInfo() {
        String teacherNo = DgbSecurityUserHelper.getCurrentUser().getUsername().split(" ")[1];
        Teacher currentTeacher = teacherService.getById(teacherNo);
        currentTeacher.setPassword(null);
        Result result = Result.success();
        result.setData(currentTeacher);
        return result;
    }
}
