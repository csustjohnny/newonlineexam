package com.csust.onlineexam.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.csust.onlineexam.dto.Result;
import com.csust.onlineexam.dto.ResultCode;
import com.csust.onlineexam.entity.Exam;
import com.csust.onlineexam.entity.Student;
import com.csust.onlineexam.service.impl.Exam2studentServiceImpl;
import com.csust.onlineexam.service.impl.ExamServiceImpl;
import com.csust.onlineexam.service.impl.StudentServiceImpl;
import com.csust.onlineexam.util.DgbSecurityUserHelper;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.servlet.ModelAndView;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author johnNick
 * @since 2020-03-18
 */
@RestController
@RequestMapping("student")
public class StudentController {

    private final StudentServiceImpl studentService;
    private final ExamServiceImpl examService;
    private final Exam2studentServiceImpl exam2studentService;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    public StudentController(StudentServiceImpl studentService, ExamServiceImpl examService, Exam2studentServiceImpl exam2studentService) {
        this.studentService = studentService;
        this.examService = examService;
        this.exam2studentService = exam2studentService;
    }

    @GetMapping("/index")
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("student");
        return modelAndView;
    }

    @GetMapping("/person_info")
    public ModelAndView showPersonInfo() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/student/personal_information");
        return modelAndView;
    }

    @GetMapping("getPersonInfo")
    @ApiOperation("获取当前用户信息")
    public Result getPersonInfo() {
        String studentNo = DgbSecurityUserHelper.getCurrentUser().getUsername().split(" ")[1];
        Student currentStudent = studentService.getById(studentNo);
        currentStudent.setPassword(null);
        Result result = Result.success();
        result.setData(currentStudent);
        return result;
    }
    @PostMapping("savePersonInfo")
    public Result savePersonInfo(@RequestBody Student student){
        if (student.getStudentNo() == null || "".equals(student.getStudentNo())) {
            return Result.failure(ResultCode.PARAMS_INCORRECT);
        }
        if(student.getPassword()!=null && !"".equals(student.getPassword())){
            student.setPassword(passwordEncoder.encode(student.getPassword()));
        } else{
            student.setPassword(null);
        }
        studentService.updateById(student);
        return Result.success();
    }
    @GetMapping("/getExams")
    public Result getExams(){
        String studentNo = DgbSecurityUserHelper.getCurrentUser().getUsername().split(" ")[1].trim();
        System.out.println(studentNo);
        QueryWrapper<Student> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("t_student.student_no",studentNo);
        Result result = Result.success();
        result.setData(exam2studentService.getStudentsExam(queryWrapper));
        return result;
    }
}
