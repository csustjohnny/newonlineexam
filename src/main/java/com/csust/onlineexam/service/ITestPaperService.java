package com.csust.onlineexam.service;

import com.csust.onlineexam.entity.ChoiceQuestion;
import com.csust.onlineexam.entity.TestPaper;
import com.baomidou.mybatisplus.extension.service.IService;

import java.awt.*;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author johnNick
 * @since 2020-05-24
 */
public interface ITestPaperService extends IService<TestPaper> {
    /**
     * 获取考试题目
     * @param examId 考试ID
     * @return 问题列表
     */
    Map<String,Object> getQuestionsByExamId(Integer examId);
}
