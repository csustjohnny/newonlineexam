package com.csust.onlineexam.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.csust.onlineexam.entity.Exam;
import com.csust.onlineexam.entity.Exam2student;
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
 * @since 2020-05-20
 */
public interface Exam2studentMapper extends BaseMapper<Exam2student> {
    /**
     * 获取学生的考试
     * @param wrapper 查询条件
     * @return 考试结果
     */
    List<Exam> getStudentsExam(  @Param(Constants.WRAPPER) Wrapper<Student> wrapper);
}
