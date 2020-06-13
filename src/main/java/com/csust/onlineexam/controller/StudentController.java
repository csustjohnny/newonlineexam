package com.csust.onlineexam.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.csust.onlineexam.constant.Constant;
import com.csust.onlineexam.dto.Result;
import com.csust.onlineexam.dto.ResultCode;
import com.csust.onlineexam.dto.StudentChoiceQuestionDTO;
import com.csust.onlineexam.entity.*;
import com.csust.onlineexam.service.impl.*;
import com.csust.onlineexam.util.DgbSecurityUserHelper;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

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
    private final TestPaperServiceImpl testPaperService;
    final StudentExamInfoServiceImpl studentExamInfoService;
    PasswordEncoder passwordEncoder;
    @Autowired
    public StudentController(StudentServiceImpl studentService, ExamServiceImpl examService, Exam2studentServiceImpl exam2studentService, TestPaperServiceImpl testPaperService, StudentExamInfoServiceImpl studentExamInfoService, PasswordEncoder passwordEncoder) {
        this.studentService = studentService;
        this.examService = examService;
        this.exam2studentService = exam2studentService;
        this.testPaperService = testPaperService;
        this.studentExamInfoService = studentExamInfoService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/index")
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("student");
        return modelAndView;
    }
    @GetMapping("/test")
    public ModelAndView test(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/student/test");
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
    @ApiOperation("获取考试信息")
    public Result getExams(){
        String studentNo = DgbSecurityUserHelper.getCurrentUser().getUsername().split(" ")[1].trim();
        QueryWrapper<Student> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("t_student.student_no",studentNo);
        //queryWrapper.lt("t_exam.end_time", LocalDateTime.now());
        Result result = Result.success();
        result.setData(exam2studentService.getStudentsExam(queryWrapper));
        return result;
    }
    @GetMapping("getSysTime")
    public Result getSystemTime(){
        Result result = Result.success();
        SimpleDateFormat df = new SimpleDateFormat("yyyy MM dd HH mm ss");
        result.setData(df.format(new Date()));
        return result;
    }
    @GetMapping("getOneExam")
    public Result getOneExam(@RequestParam Integer examId){
        Exam exam = examService.getById(examId);
        if(exam==null){
            return  Result.failure(ResultCode.NO_EXIST);
        } else{
            Result result = Result.success();
            result.setData(exam);
            return result;
        }
    }
    @GetMapping("/getExamQuestions")
    public Result getExamQuestions(@RequestParam Integer examId){
        Result result = Result.success();
        Map<String,Object> questions = testPaperService.getQuestionsByExamId(examId);
        result.setData(questions);
        return result;
    }
    @PostMapping("submitQuestions")
    public Result submitQuestions(List<StudentChoiceQuestionDTO> choiceQuestionDTOList){
        choiceQuestionDTOList.forEach(System.out::println);
        return Result.success();
    }
    @PostMapping("submitTestPaper")
    @Transactional(rollbackFor = Exception.class)
    public Result submitTestPaper(HttpServletRequest request) throws Exception {
        Integer examId = Integer.valueOf(request.getParameter("examId"));
        StudentExamInfo examInfo = null;
        String studentNo = DgbSecurityUserHelper.getCurrentUser().getUsername().split(" ")[1];
        //获取试卷
        List<TestPaper> paperList = testPaperService.list(new QueryWrapper<TestPaper>().eq("exam_id",examId));
        //录入选择题答案
        List<Integer> choiceQuestionIdList = paperList.stream()
                .filter(testPaper -> Constant.CHOICE_QUESTION_TYPE.equals(testPaper.getQuestionType()))
                .map(TestPaper::getQuestionId).collect(Collectors.toList());
        for (Integer questionId : choiceQuestionIdList){
            examInfo = new StudentExamInfo();
            examInfo.setAnswer(request.getParameter("ch_a_"+questionId));
            examInfo.setExamId(examId);
            examInfo.setQuestionType("选择题");
            examInfo.setStudentNo(studentNo);
            examInfo.setQuestionId(questionId);
            studentExamInfoService.saveOrUpdateEntity(examInfo);
        }
        //录入填空题
        List<Integer> fillingQuestionList = paperList.stream()
                .filter(testPaper -> Constant.FILLING_IN_THE_BLANK_QUESTION_TYPE.equals(testPaper.getQuestionType()))
                .map(TestPaper::getQuestionId).collect(Collectors.toList());
        for (Integer questionId : fillingQuestionList){
            examInfo = new StudentExamInfo();
            examInfo.setAnswer(request.getParameter("fi_a_"+questionId));
            examInfo.setExamId(examId);
            examInfo.setQuestionType("填空题");
            examInfo.setStudentNo(studentNo);
            examInfo.setQuestionId(questionId);
            studentExamInfoService.saveOrUpdateEntity(examInfo);
        }
        //录入判断题
        List<Integer> judgementIdList = paperList.stream()
                .filter(testPaper -> Constant.JUDGEMENT_QUESTION_TYPE.equals(testPaper.getQuestionType()))
                .map(TestPaper::getQuestionId).collect(Collectors.toList());
        for(Integer questionId : judgementIdList){
            examInfo = new StudentExamInfo();
            String answer;
            if("true".equals(request.getParameter("judgement_a_"+questionId))){
                answer = "true";
            } else if("false".equals(request.getParameter("judgement_a_"+questionId))){
                answer = "false";
            } else {
                answer = "";
            }
            examInfo.setAnswer(answer);
            examInfo.setExamId(examId);
            examInfo.setQuestionType("判断题");
            examInfo.setStudentNo(studentNo);
            examInfo.setQuestionId(questionId);
            System.out.println(examInfo);
            studentExamInfoService.saveOrUpdateEntity(examInfo);
        }
        //录入编程题答案
        List<Integer> codeQuestionIdList = paperList.stream()
                .filter(testPaper -> Constant.CODE_QUESTION_TYPE.equals(testPaper.getQuestionType()))
                .map(TestPaper::getQuestionId).collect(Collectors.toList());
        for(Integer questionId : codeQuestionIdList){
            examInfo = new StudentExamInfo();
            examInfo.setAnswer(request.getParameter("fi_a_"+questionId));
            examInfo.setExamId(examId);
            examInfo.setQuestionType("编程题");
            examInfo.setStudentNo(studentNo);
            examInfo.setQuestionId(questionId);
            studentExamInfoService.saveOrUpdateEntity(examInfo);
        }
        return Result.success();
    }
    @GetMapping("/getExamScore")
    public Result getScoreByExamId(@RequestParam Integer examId){
        String studentNo = DgbSecurityUserHelper.getCurrentUser().getUsername().split(" ")[1];
        Result result = Result.success();
        result.setData(studentExamInfoService.getScoreByStudentExamId(studentNo,examId));
        return result;

    }
}
