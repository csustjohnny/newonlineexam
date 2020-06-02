package com.csust.onlineexam.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.csust.onlineexam.entity.TestPaper;
import com.csust.onlineexam.mapper.*;
import com.csust.onlineexam.service.ITestPaperService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author johnNick
 * @since 2020-05-24
 */
@Service
public class TestPaperServiceImpl extends ServiceImpl<TestPaperMapper, TestPaper> implements ITestPaperService {

    final ChoiceQuestionMapper choiceQuestionMapper;
    final JudgementMapper judgementMapper;
    final FillingInTheBlankQuestionMapper fillingInTheBlankQuestionMapper;
    final CodeQuestionMapper codeQuestionMapper;
    @Autowired
    public TestPaperServiceImpl(ChoiceQuestionMapper choiceQuestionMapper, JudgementMapper judgementMapper, FillingInTheBlankQuestionMapper fillingInTheBlankQuestionMapper, CodeQuestionMapper codeQuestionMapper) {
        this.choiceQuestionMapper = choiceQuestionMapper;
        this.judgementMapper = judgementMapper;
        this.fillingInTheBlankQuestionMapper = fillingInTheBlankQuestionMapper;
        this.codeQuestionMapper = codeQuestionMapper;
    }

    @Override
    public Map<String, Object> getQuestionsByExamId(Integer examId) {
        Map<String,Object> questions = new HashMap<>(4);
        questions.put("choice_question",choiceQuestionMapper.getStudentChoiceQuestionsByExamId(examId));
        questions.put("judgement_question",judgementMapper.getStudentJudgementsByExamId(examId));
        questions.put("filling_question",fillingInTheBlankQuestionMapper.getStudentFillingQuestionsByExamId(examId));
        questions.put("code_question",codeQuestionMapper.getStudentCodeQuestionByExamId(examId));
        return questions;
    }
}
