package com.csust.onlineexam;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.csust.onlineexam.entity.Student;
import com.csust.onlineexam.mapper.StudentMapper;
import com.csust.onlineexam.service.impl.StudentServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

@SpringBootTest
public class UserTest {
    @Autowired
    private StudentMapper studentMapper;
    @Autowired
    StudentServiceImpl studentService;

    @Test
    void insertTest(){
        Student student = new Student();
        student.setName("单云彪");
        student.setStudentNo("201616080307");
        student.setClassId(1);
        student.setSex("男");
        student.setPhone("18507318074");
        student.setPassword("$2a$10$9EYMVmVLDDOoCcN5xmpW4OE8FVaf3CN55ccDucn6w0u0JIazX.Xje");
        studentMapper.insert(student);
    }
    @Test
    void testPage(){
        QueryWrapper<Student> queryWrapper = new QueryWrapper<>();
        Page<Student> page = new Page<>(1, 2);
        IPage<Student> studentPage = studentMapper.selectPage(page, queryWrapper);
        System.out.println(studentPage.getPages());
        System.out.println(studentPage.getTotal());
        studentPage.getRecords().forEach(System.out::println);
    }
    @Test
    void studentInfoTest(){
        QueryWrapper<Student> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("sex","男");
        Page<Map<String,Object>> page = new Page<>(1, 5);
        System.out.println(studentService.getStudentInfoList(page,queryWrapper));
        studentMapper.getStudentInfoList(page,queryWrapper).forEach(System.out::println);
    }
}
