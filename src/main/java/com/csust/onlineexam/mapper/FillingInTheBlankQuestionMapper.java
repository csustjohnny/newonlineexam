package com.csust.onlineexam.mapper;

import com.csust.onlineexam.dto.StudentChoiceQuestionDTO;
import com.csust.onlineexam.dto.StudentFillingQuestionDTO;
import com.csust.onlineexam.entity.FillingInTheBlankQuestion;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

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
}
