package com.csust.onlineexam.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.csust.onlineexam.dto.StudentJudgementQuestionDTO;
import com.csust.onlineexam.entity.Judgement;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.csust.onlineexam.entity.Student;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author johnNick
 * @since 2020-04-10
 */
public interface JudgementMapper extends BaseMapper<Judgement> {
    /**
     * 根据考试ID获取判断题列表
     * @param examId 考试ID
     * @return 判断题列表
     */
    List<StudentJudgementQuestionDTO> getStudentJudgementsByExamId(Integer examId);

    /**
     * 随机获取指定数量的题目
     * @param count 题目个数
     * @param wrapper 查询条件
     * @return 题目列表
     */
    List<Judgement> getRandomQuestion(Integer count, @Param(Constants.WRAPPER) Wrapper<Judgement> wrapper);
}
