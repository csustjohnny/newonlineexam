package com.csust.onlineexam.mapper;

import com.csust.onlineexam.dto.StudentChoiceQuestionDTO;
import com.csust.onlineexam.entity.ChoiceQuestion;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

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
}
