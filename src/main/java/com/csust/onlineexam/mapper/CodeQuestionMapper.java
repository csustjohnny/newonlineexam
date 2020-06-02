package com.csust.onlineexam.mapper;

import com.csust.onlineexam.dto.StudentCodeQuestionDTO;
import com.csust.onlineexam.entity.CodeQuestion;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author johnNick
 * @since 2020-05-04
 */
public interface CodeQuestionMapper extends BaseMapper<CodeQuestion> {
    /**
     * 根据考试ID
     * @param id 考试ID
     * @return 编程题列表
     */
    List<StudentCodeQuestionDTO> getStudentCodeQuestionByExamId(Integer id);
}
