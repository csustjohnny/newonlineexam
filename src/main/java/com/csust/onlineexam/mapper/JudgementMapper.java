package com.csust.onlineexam.mapper;

import com.csust.onlineexam.dto.StudentJudgementQuestionDTO;
import com.csust.onlineexam.entity.Judgement;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

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
}
