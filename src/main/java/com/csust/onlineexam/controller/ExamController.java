package com.csust.onlineexam.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.csust.onlineexam.constant.Constant;
import com.csust.onlineexam.dto.ExamDTO;
import com.csust.onlineexam.dto.PaginationDTO;
import com.csust.onlineexam.dto.Result;
import com.csust.onlineexam.dto.ResultCode;
import com.csust.onlineexam.entity.Exam;
import com.csust.onlineexam.entity.Judgement;
import com.csust.onlineexam.service.impl.ExamServiceImpl;
import com.csust.onlineexam.util.DgbSecurityUserHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.servlet.ModelAndView;
import sun.util.calendar.BaseCalendar;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author johnNick
 * @since 2020-05-20
 */
@RestController
@RequestMapping("/exam")
@Api(tags = "考试相关接口")
public class ExamController {

    private final ExamServiceImpl examService;

    public ExamController(ExamServiceImpl examService) {
        this.examService = examService;
    }

    @GetMapping("exam_management")
    public ModelAndView showExamManagement() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("exam/exam_management");
        return modelAndView;
    }

    @GetMapping("exam_add")
    public ModelAndView showExamAdd() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("exam/exam_add");
        return modelAndView;
    }

    @GetMapping("getCreatedExams")
    @ApiOperation("获取考试列表")
    public PaginationDTO getCreatedExams(@RequestParam(name = "current", defaultValue = "1") int current,
                                         @RequestParam(name = "limit", defaultValue = "15") int limit) {
        QueryWrapper<Exam> queryWrapper = new QueryWrapper<>();
        if (DgbSecurityUserHelper.getRoleList().contains(Constant.ROLE_TEACHER)) {
            System.out.println(DgbSecurityUserHelper.getCurrentUser().getUsername().split(" ")[1]);
            queryWrapper.eq("create_teacher", DgbSecurityUserHelper.getCurrentUser().getUsername().split(" ")[1]);
        }

        Page<Exam> examPage = examService.page(new Page<>(current, limit), queryWrapper);
        return new PaginationDTO(0, examPage.getRecords(), examPage.getTotal());
    }
    @PostMapping("saveOrUpdateExam")
    public Result saveOrUpdateExam(@RequestBody ExamDTO examDTO){
        Exam exam = new Exam();
        BeanUtils.copyProperties(examDTO,exam);
        exam.setStartTime(LocalDateTime.parse(examDTO.getStartTime().replace(' ', 'T')));
        exam.setEndTime(LocalDateTime.parse(examDTO.getEndTime().replace(' ','T')));
        if(exam.getExamId()==null){
            //添加试卷
            if (DgbSecurityUserHelper.getRoleList().contains(Constant.ROLE_TEACHER)) {
                exam.setCreateTeacher(DgbSecurityUserHelper.getCurrentUser().getUsername().split(" ")[1]);
            }
            exam.setCreateTime(LocalDateTime.now());
            exam.setModifyTime(exam.getCreateTime());
            examService.save(exam);
        }else {
            //更新试卷
            if (DgbSecurityUserHelper.getRoleList().contains(Constant.ROLE_TEACHER)
                    && exam.getCreateTeacher().equals(DgbSecurityUserHelper.getCurrentUser().getUsername().split(" ")[1])) {
                exam.setModifyTime(LocalDateTime.now());
                examService.updateById(exam);
            } else {
                return Result.failure(ResultCode.AUTHORITY_ERROR);
            }

        }
        System.out.println(exam);
        return Result.success();
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
    @PostMapping("deleteOneExam")
    public Result deleteOneExam(@RequestParam Integer examId){
        return examService.removeById(examId)?Result.success():Result.failure(ResultCode.DELETE_FAILURE);
    }
}
