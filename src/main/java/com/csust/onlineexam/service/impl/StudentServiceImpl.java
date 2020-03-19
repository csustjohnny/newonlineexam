package com.csust.onlineexam.service.impl;

import com.csust.onlineexam.entity.Student;
import com.csust.onlineexam.mapper.StudentMapper;
import com.csust.onlineexam.service.IStudentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

}
