package com.csust.onlineexam;

import com.csust.onlineexam.entity.ClassInfo;
import com.csust.onlineexam.entity.Department;
import com.csust.onlineexam.entity.School;
import com.csust.onlineexam.mapper.ClassInfoMapper;
import com.csust.onlineexam.mapper.DepartmentMapper;
import com.csust.onlineexam.mapper.InstitutionMapper;
import com.csust.onlineexam.mapper.SchoolMapper;
import com.csust.onlineexam.service.impl.DepartmentServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @author ：Lenovo.
 * @date ：Created in 12:59 2020/3/18
 */
@SpringBootTest
public class InstitutionTest {
    @Autowired
    private SchoolMapper schoolMapper;
    @Autowired
    private DepartmentMapper departmentMapper;
    @Autowired
    private ClassInfoMapper classInfoMapper;
    @Autowired
    private InstitutionMapper institutionMapper;
    @Autowired
    private DepartmentServiceImpl departmentService;
    @Test
    void addSchoolTest(){
        School school = new School();
        school.setSchoolName("计算机与通信工程学院");
        schoolMapper.insert(school);
        System.out.println(school.getSchoolCode());
    }
    @Test
    void addDepartmentTest(){
        Department department = new Department();
        department.setSchoolCode(1);
        department.setDepartmentName("软件工程系");
        departmentMapper.insert(department);
        System.out.println(department.getDepartmentId());
    }
    @Test
    void addClassInfoTest(){
        ClassInfo classInfo = new ClassInfo();
        classInfo.setDepartmentId(1);
        classInfo.setClassName("软件工程1604班");
        classInfo.setClassNo("1604");
        classInfoMapper.insert(classInfo);
        System.out.println(classInfo.getClassId());
    }
    @Test
    void showInstitutionTest(){
        List<Integer> schoolCodeList = institutionMapper.getAllSchoolCode();
        schoolCodeList.forEach(code -> System.out.println(institutionMapper.selectSchoolDepartmentInfoBySchoolCode(code)));
    }
    @Test
    void updateInstitutionTest(){
        Department department = new Department();
        department.setDepartmentId(1);
        department.setDepartmentName("软件工程系");
        System.out.println(departmentService.updateById(department));
    }


}
