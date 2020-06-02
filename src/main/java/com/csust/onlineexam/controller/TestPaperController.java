package com.csust.onlineexam.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.csust.onlineexam.constant.Constant;
import com.csust.onlineexam.dto.Result;
import com.csust.onlineexam.dto.ResultCode;
import com.csust.onlineexam.entity.*;
import com.csust.onlineexam.service.impl.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.servlet.ModelAndView;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author johnNick
 * @since 2020-05-24
 */
@RestController
@RequestMapping("/test-paper")
public class TestPaperController {

    private final TestPaperServiceImpl testPaperService;
    private final ChoiceQuestionServiceImpl choiceQuestionService;
    private final FillingInTheBlankQuestionServiceImpl fillingInTheBlankQuestionService;
    private final JudgementServiceImpl judgementService;
    private final CodeQuestionServiceImpl codeQuestionService;
    @Autowired
    public TestPaperController(TestPaperServiceImpl testPaperService, ChoiceQuestionServiceImpl choiceQuestionService, FillingInTheBlankQuestionServiceImpl fillingInTheBlankQuestionService, JudgementServiceImpl judgementService, CodeQuestionServiceImpl codeQuestionService) {
        this.testPaperService = testPaperService;
        this.choiceQuestionService = choiceQuestionService;
        this.fillingInTheBlankQuestionService = fillingInTheBlankQuestionService;
        this.judgementService = judgementService;
        this.codeQuestionService = codeQuestionService;
    }

    @GetMapping("test_paper_management")
    public ModelAndView showTestPaper(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/test/test_paper_management");
        return modelAndView;
    }
    @GetMapping("getExamQuestions")
    public Result getTestPaper(@RequestParam Integer examId){
        Result result = Result.success();
        //获取试卷
        List<TestPaper> paperList = testPaperService.list(new QueryWrapper<TestPaper>().eq("exam_id",examId));
        //paperList.forEach(System.out::println);
        //选择题列表
        List<Integer> choiceQuestionIdList = paperList.stream()
                .filter(testPaper -> Constant.CHOICE_QUESTION_TYPE.equals(testPaper.getQuestionType()))
                .map(TestPaper::getQuestionId).collect(Collectors.toList());
        List<ChoiceQuestion> choiceQuestionList = new ArrayList<>(0);
        if(choiceQuestionIdList.size()>0) {
            choiceQuestionList = choiceQuestionService.listByIds(choiceQuestionIdList);
        }
        //填空题列表
        List<Integer> fillingBlankQuestionIdList = paperList.stream()
                .filter(testPaper -> Constant.FILLING_IN_THE_BLANK_QUESTION_TYPE.equals(testPaper.getQuestionType())).sorted()
                .map(TestPaper::getQuestionId).collect(Collectors.toList());
        List<FillingInTheBlankQuestion> fillingInTheBlankQuestionList = new ArrayList<>(0);
        if(fillingBlankQuestionIdList.size()>0) {
            fillingInTheBlankQuestionList = fillingInTheBlankQuestionService.listByIds(fillingBlankQuestionIdList);
        }

        //判断题列表
        List<Integer> judgementQuestionIdList = paperList.stream()
                .filter(testPaper -> Constant.JUDGEMENT_QUESTION_TYPE.equals(testPaper.getQuestionType()))
                .map(TestPaper::getQuestionId).collect(Collectors.toList());
        List<Judgement> judgementList = new ArrayList<>(0);
        if(judgementQuestionIdList.size()>0) {
            judgementList = judgementService.listByIds(judgementQuestionIdList);
        }
        //编程题列表
        List<Integer> codeQuestionIdList = paperList.stream()
                .filter(testPaper -> Constant.CODE_QUESTION_TYPE.equals(testPaper.getQuestionType()))
                .map(TestPaper::getQuestionId).collect(Collectors.toList());
        List<CodeQuestion> codeQuestionList = new ArrayList<>(0);
        if(codeQuestionIdList.size()>0) {
            codeQuestionList = codeQuestionService.listByIds(codeQuestionIdList);
        }
        Map<String,Object> questionList = new HashMap<>(3);
        questionList.put("choice_question",choiceQuestionList);
        questionList.put("judgement_question",judgementList);
        questionList.put("filling_blank_question",fillingInTheBlankQuestionList);
        questionList.put("code_question",codeQuestionList);
        result.setData(questionList);
        return result;
    }
    @PostMapping("addPaperQuestion")
    public Result addQuestion(@RequestBody TestPaper testPaper){
        return testPaperService.save(testPaper)?Result.success():Result.failure(ResultCode.INSERT_FAILURE);
    }
}
