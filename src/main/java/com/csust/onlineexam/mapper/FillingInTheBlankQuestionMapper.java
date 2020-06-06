package com.csust.onlineexam.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.csust.onlineexam.dto.StudentChoiceQuestionDTO;
import com.csust.onlineexam.dto.StudentFillingQuestionDTO;
import com.csust.onlineexam.entity.FillingInTheBlankQuestion;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.csust.onlineexam.entity.Judgement;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author johnNick
 * @since 2020-04-12
 */
public interface FillingInTheBlankQuestionMapper extends BaseMapper<FillingInTheBlankQuestion> {
    /**
     * 根据考试ID获取填空题列表
     * @param examId 考试ID
     * @return 填空题列表
     */
    List<StudentFillingQuestionDTO> getStudentFillingQuestionsByExamId(Integer examId);
    /**
     * 随机获取指定数量的题目
     * @param count 题目个数
     * @param wrapper 查询条件
     * @return 题目列表
     */
    List<FillingInTheBlankQuestion> getRandomQuestion(Integer count, @Param(Constants.WRAPPER) Wrapper<FillingInTheBlankQuestion> wrapper);
}
