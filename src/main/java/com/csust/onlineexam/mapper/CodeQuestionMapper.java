package com.csust.onlineexam.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.csust.onlineexam.dto.StudentCodeQuestionDTO;
import com.csust.onlineexam.entity.CodeQuestion;
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
 * @since 2020-05-04
 */
public interface CodeQuestionMapper extends BaseMapper<CodeQuestion> {
    /**
     * 根据考试ID
     * @param id 考试ID
     * @return 编程题列表
     */
    List<StudentCodeQuestionDTO> getStudentCodeQuestionByExamId(Integer id);
    /**
     * 随机获取指定数量的题目
     * @param count 题目个数
     * @param wrapper 查询条件
     * @return 题目列表
     */
    List<CodeQuestion> getRandomQuestion(Integer count, @Param(Constants.WRAPPER) Wrapper<CodeQuestion> wrapper);
}
