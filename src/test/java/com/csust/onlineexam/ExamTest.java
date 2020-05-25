package com.csust.onlineexam;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.csust.onlineexam.dto.ExamDTO;
import com.csust.onlineexam.entity.Exam;
import com.csust.onlineexam.entity.Student;
import com.csust.onlineexam.service.impl.Exam2studentServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

/**
 * @author ：Lenovo
 * @date ：Created in 2020/5/20 15:42
 * @description : Exam Test
 * @modified By：
 */
@SpringBootTest
public class ExamTest {

    @Autowired
    Exam2studentServiceImpl exam2studentService;
    @Test
    public void testAddExam(){
        String str = "2020-05-20 21:44:18";
        str = str.replace(' ','T');
        System.out.println(str);
    }
    @Test
    public void testExam(){
        ExamDTO examDTO = new ExamDTO();
        examDTO.setExamId(1);
        examDTO.setStartTime("dskskdk");
        examDTO.setExamTitle("ksdfl");
        Exam exam = new Exam();
        BeanUtils.copyProperties(examDTO,exam);
        System.out.println(exam);
    }
    @Test
    public void testGetExam(){
        QueryWrapper<Student> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("t_student.student_no","201616080307");
        exam2studentService.getStudentsExam(queryWrapper).forEach(System.out::println);
    }
}
