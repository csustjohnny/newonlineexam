package com.csust.onlineexam.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.csust.onlineexam.entity.Admin;
import com.csust.onlineexam.entity.Student;
import com.csust.onlineexam.entity.Teacher;
import com.csust.onlineexam.mapper.AdminMapper;
import com.csust.onlineexam.mapper.StudentMapper;
import com.csust.onlineexam.mapper.TeacherMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author ：Lenovo.
 * @date ：Created in 14:31 2020/3/18
 */
@Service
public class UserServiceImpl implements UserDetailsService {

    private final AdminMapper adminMapper;

    private final TeacherMapper teacherMapper;

    private final StudentMapper studentMapper;

    public static Integer USER_TYPE = 0;

    @Autowired
    public UserServiceImpl(AdminMapper adminMapper, TeacherMapper teacherMapper, StudentMapper studentMapper) {
        this.adminMapper = adminMapper;
        this.teacherMapper = teacherMapper;
        this.studentMapper = studentMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        if (USER_TYPE == 0) {
            //学生登录
            Student student = studentMapper.selectById(s);
            if (student == null) {
                throw new UsernameNotFoundException("用户账号不存在");
            }
            return new User(student.getName() + " " + student.getStudentNo(), student.getPassword(), AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_STUDENT"));
        } else if (USER_TYPE == 1) {
            //教师登录
            Teacher teacher = teacherMapper.selectById(s);
            if (teacher == null) {
                throw new UsernameNotFoundException("用户账号不存在");
            }
            return new User(teacher.getTeacherName() + " " + teacher.getTeacherNo(), teacher.getPassword(), AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_TEACHER"));
        } else {
            //管理员登录
            QueryWrapper<Admin> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("admin_name", s);
            Admin admin = adminMapper.selectOne(queryWrapper);
            if (admin == null) {
                throw new UsernameNotFoundException("用户账号不存在");
            }
            return new User(s, admin.getAdminPassword(), AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_ADMIN"));
        }
    }
}
