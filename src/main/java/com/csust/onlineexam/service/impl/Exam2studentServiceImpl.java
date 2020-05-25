package com.csust.onlineexam.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.csust.onlineexam.entity.Exam;
import com.csust.onlineexam.entity.Exam2student;
import com.csust.onlineexam.entity.Student;
import com.csust.onlineexam.mapper.Exam2studentMapper;
import com.csust.onlineexam.service.IExam2studentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author johnNick
 * @since 2020-05-20
 */
@Service
public class Exam2studentServiceImpl extends ServiceImpl<Exam2studentMapper, Exam2student> implements IExam2studentService {

    @Override
    public List<Exam> getStudentsExam(Wrapper<Student> queryWrapper) {
        return this.baseMapper.getStudentsExam(queryWrapper);
    }
}
