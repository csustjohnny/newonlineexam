package com.csust.onlineexam.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.csust.onlineexam.entity.Exam;
import com.csust.onlineexam.entity.Exam2student;
import com.baomidou.mybatisplus.extension.service.IService;
import com.csust.onlineexam.entity.Student;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author johnNick
 * @since 2020-05-20
 */
public interface IExam2studentService extends IService<Exam2student> {

    /**
     * 查询学生的考试
     * @param queryWrapper 查询条件
     * @return 考试列表
     */
    List<Exam> getStudentsExam(Wrapper<Student> queryWrapper);
}
