package com.csust.onlineexam.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.csust.onlineexam.dto.StudentChoiceQuestionDTO;
import com.csust.onlineexam.entity.ChoiceQuestion;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.csust.onlineexam.entity.Judgement;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author johnNick
 * @since 2020-04-05
 */
public interface ChoiceQuestionMapper extends BaseMapper<ChoiceQuestion> {
    /**
     * 获取试卷的所有选择题
     * @param examId 考试id
     * @return 选择题信息
     */
    List<StudentChoiceQuestionDTO> getStudentChoiceQuestionsByExamId(Integer examId);
    /**
     * 随机获取指定数量的题目
     * @param count 题目个数
     * @param wrapper 查询条件
     * @return 题目列表
     */
    List<ChoiceQuestion> getRandomQuestion(Integer count, @Param(Constants.WRAPPER) Wrapper<ChoiceQuestion> wrapper);

    List<Map<String,Double>> getGradeAnalysisByStudent(String studentNo);

    Integer getScoreByExamIdStudent(String studentNo,Integer examId);

}
