package com.csust.onlineexam.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.csust.onlineexam.constant.Constant;
import com.csust.onlineexam.dto.*;
import com.csust.onlineexam.entity.*;
import com.csust.onlineexam.service.impl.*;
import com.csust.onlineexam.util.DgbSecurityUserHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.util.List;

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
    final CodeQuestionServiceImpl codeQuestionService;
    final FillingInTheBlankQuestionServiceImpl fillingInTheBlankQuestionService;
    final JudgementServiceImpl judgementService;
    final ChoiceQuestionServiceImpl choiceQuestionService;
    final TestPaperServiceImpl testPaperService;
    final Exam2studentServiceImpl exam2studentService;
    public ExamController(ExamServiceImpl examService, CodeQuestionServiceImpl codeQuestionService, FillingInTheBlankQuestionServiceImpl fillingInTheBlankQuestionService, JudgementServiceImpl judgementService, ChoiceQuestionServiceImpl choiceQuestionService, TestPaperServiceImpl testPaperService, Exam2studentServiceImpl exam2studentService) {
        this.examService = examService;
        this.codeQuestionService = codeQuestionService;
        this.fillingInTheBlankQuestionService = fillingInTheBlankQuestionService;
        this.judgementService = judgementService;
        this.choiceQuestionService = choiceQuestionService;
        this.testPaperService = testPaperService;
        this.exam2studentService = exam2studentService;
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

    @GetMapping("auto_test_paper_generating")
    public ModelAndView showAutoGenerateTestPaper() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("form/auto_test_paper_generating");
        return modelAndView;
    }

    @GetMapping("exam_edit")
    public ModelAndView showExamEdit() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("exam/exam_edit");
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
    public Result saveOrUpdateExam(@RequestBody ExamDTO examDTO,
                                   @RequestParam String studentNo) {
        Exam exam = new Exam();
        BeanUtils.copyProperties(examDTO, exam);
        exam.setStartTime(LocalDateTime.parse(examDTO.getStartTime().replace(' ', 'T')));
        exam.setEndTime(LocalDateTime.parse(examDTO.getEndTime().replace(' ', 'T')));
        if (exam.getExamId() == null) {
            //添加试卷
            if (DgbSecurityUserHelper.getRoleList().contains(Constant.ROLE_TEACHER)) {
                exam.setCreateTeacher(DgbSecurityUserHelper.getCurrentUser().getUsername().split(" ")[1]);
            }
            exam.setCreateTime(LocalDateTime.now());
            exam.setModifyTime(exam.getCreateTime());
            examService.save(exam);
        } else {
            //更新试卷
            if (DgbSecurityUserHelper.getRoleList().contains(Constant.ROLE_TEACHER)
                    && exam.getCreateTeacher().equals(DgbSecurityUserHelper.getCurrentUser().getUsername().split(" ")[1])) {
                exam.setModifyTime(LocalDateTime.now());
                examService.updateById(exam);
            } else {
                return Result.failure(ResultCode.AUTHORITY_ERROR);
            }
        }
        Exam2student exam2Student = new Exam2student();
        exam2Student.setExamId(exam.getExamId());
        exam2Student.setStudentNo(studentNo);
        System.out.println(exam2Student);
        exam2studentService.save(exam2Student);
        return Result.success();
    }

    @GetMapping("getOneExam")
    public Result getOneExam(@RequestParam Integer examId) {
        Exam exam = examService.getById(examId);
        if (exam == null) {
            return Result.failure(ResultCode.NO_EXIST);
        } else {
            Result result = Result.success();
            result.setData(exam);
            return result;
        }
    }

    @PostMapping("deleteOneExam")
    public Result deleteOneExam(@RequestParam Integer examId) {
        return examService.removeById(examId) ? Result.success() : Result.failure(ResultCode.DELETE_FAILURE);
    }

    @PostMapping("autoGenerateTestPaper")
    @Transactional(rollbackFor = Exception.class)
    public Result autoGenerateTestPaper(@RequestBody AutoGenerateTestPaperDTO generateInfo){
        //随机添加选择题
        TestPaper testPaper = new TestPaper();
        List<ChoiceQuestion> choiceQuestionList = choiceQuestionService.getBaseMapper()
                .getRandomQuestion(generateInfo.getChoiceQuestionCount(),
                        new QueryWrapper<ChoiceQuestion>().eq("subject_subordinate",generateInfo.getSubjectId()));
        for (int index = 0; index < choiceQuestionList.size(); index++) {
            testPaper.setExamId(generateInfo.getExamId());
            testPaper.setQuestionType("choice_question");
            testPaper.setScore(generateInfo.getScorePerChoiceQuestion());
            testPaper.setQuestionOrder(index+1);
            testPaper.setQuestionId(choiceQuestionList.get(index).getId());
            testPaperService.save(testPaper);
        }
        //随机添加填空题
        List<FillingInTheBlankQuestion> fillingInTheBlankQuestions
                = fillingInTheBlankQuestionService.getBaseMapper()
                .getRandomQuestion(generateInfo.getFillingQuestionCount(),
                        new QueryWrapper<FillingInTheBlankQuestion>()
                .eq("subject_subordinate",generateInfo.getSubjectId()));
        for (int index = 0; index < fillingInTheBlankQuestions.size(); index++) {
            testPaper.setExamId(generateInfo.getExamId());
            testPaper.setQuestionType("filling_in_the_blank_question");
            testPaper.setScore(generateInfo.getScorePerFillingQuestion());
            testPaper.setQuestionOrder(index+1);
            testPaper.setQuestionId(fillingInTheBlankQuestions.get(index).getQuestionId());
            testPaperService.save(testPaper);
        }
        //随机添加判断题
        List<Judgement> judgementList
                = judgementService.getBaseMapper()
                .getRandomQuestion(generateInfo.getJudgementQuestionCount(),
                        new QueryWrapper<Judgement>().eq("subject_subordinate",generateInfo.getSubjectId()));
        for (int index = 0; index < judgementList.size(); index++) {
            testPaper.setExamId(generateInfo.getExamId());
            testPaper.setQuestionType("judgement_question");
            testPaper.setScore(generateInfo.getScorePerJudgementQuestion());
            testPaper.setQuestionOrder(index+1);
            testPaper.setQuestionId(judgementList.get(index).getId());
            testPaperService.save(testPaper);
        }
        //随机添加编程题
        List<CodeQuestion> codeQuestionList
                = codeQuestionService.getBaseMapper()
                .getRandomQuestion(generateInfo.getCodeQuestionCount(),
                        new QueryWrapper<CodeQuestion>());
        for (int index = 0; index < codeQuestionList.size(); index++) {
            testPaper.setExamId(generateInfo.getExamId());
            testPaper.setQuestionType("code_question");
            testPaper.setScore(generateInfo.getScorePerCodeQuestion());
            testPaper.setQuestionOrder(index+1);
            testPaper.setQuestionId(codeQuestionList.get(index).getId());
            testPaperService.save(testPaper);
        }
        return Result.success();
    }
}
