package com.csust.onlineexam.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.csust.onlineexam.entity.Exam;
import com.csust.onlineexam.entity.Student;
import com.csust.onlineexam.mapper.StudentMapper;
import com.csust.onlineexam.service.IStudentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author johnNick
 * @since 2020-03-18
 */
@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements IStudentService {

    @Override
    public Page<Map<String, Object>> getStudentInfoList(Page<Map<String, Object>> page, Wrapper<Student> studentWrapper) {
        return page.setRecords(this.baseMapper.getStudentInfoList(page,studentWrapper));
    }
}
