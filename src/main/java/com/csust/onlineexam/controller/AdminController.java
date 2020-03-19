package com.csust.onlineexam.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.csust.onlineexam.entity.*;
import com.csust.onlineexam.service.impl.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author johnNick
 * @since 2020-03-18
 */
@RestController
@RequestMapping("/admin")
@Api(tags = "管理员相关接口")
public class AdminController {

    private final StudentServiceImpl studentService;
    private final InstitutionServiceImpl institutionService;
    private final TeacherServiceImpl teacherService;
    private final SchoolServiceImpl schoolService;
    private final DepartmentServiceImpl departmentService;
    private final ClassInfoServiceImpl classInfoService;
    @Autowired
    public AdminController(StudentServiceImpl studentService, InstitutionServiceImpl institutionService, TeacherServiceImpl teacherService, SchoolServiceImpl schoolService, DepartmentServiceImpl departmentService, ClassInfoServiceImpl classInfoService) {
        this.studentService = studentService;
        this.institutionService = institutionService;
        this.teacherService = teacherService;
        this.schoolService = schoolService;
        this.departmentService = departmentService;
        this.classInfoService = classInfoService;
    }

    @GetMapping("/index")
    @ApiOperation("管理员主页界面")
    public ModelAndView admin() {
        ModelAndView admin = new ModelAndView();
        admin.setViewName("admin");
        return admin;
    }

    @GetMapping("welcome")
    @ApiOperation("管理员首页界面")
    public ModelAndView adminWelcome() {
        ModelAndView adminWelcome = new ModelAndView();
        adminWelcome.setViewName("admin_welcome");
        return adminWelcome;
    }

    @GetMapping("/student_management")
    @ApiOperation("学生管理界面")
    public ModelAndView studentManagement() {
        ModelAndView studentManagement = new ModelAndView();
        studentManagement.setViewName("student_management");
        return studentManagement;
    }

    @GetMapping("teacher_management")
    @ApiOperation("教师管理界面")
    public ModelAndView teacherManagement() {
        ModelAndView teacherManagement = new ModelAndView();
        teacherManagement.setViewName("admin/teacher/teacher_management");
        return teacherManagement;
    }

    @GetMapping("institution_management")
    @ApiOperation("机构管理界面")
    public ModelAndView institutionManagement() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("institution_management");
        return modelAndView;
    }

    @GetMapping("getAllStudents")
    @ApiOperation("获取所有学生信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "当前页数", defaultValue = "1"),
            @ApiImplicitParam(name = "limit", value = "每页数量大小", defaultValue = "10")
    })
    public Map<String,Object> getAllStudents(@RequestParam(value = "page", defaultValue = "1") Integer pageNum,
                                        @RequestParam(value = "limit", defaultValue = "10") Integer size,
                                        @RequestParam(value = "school", required = false) String school) {
        System.out.println(school);
        QueryWrapper<Student> queryWrapper = new QueryWrapper<>();
        queryWrapper.select(Student.class,tableFieldInfo -> !"password".equals(tableFieldInfo.getColumn()));
        Page<Student> page = new Page<>(pageNum, size);
        Map<String, Object> result = new HashMap<>(2);
        result.put("code",0);
        Page<Student> studentPage = studentService.page(page, queryWrapper);
        result.put("data",studentPage.getRecords());
        result.put("count",studentPage.getTotal());
        return result;
    }

    @GetMapping("getAllInstitution")
    @ApiOperation("获取所有机构信息")
    public List<InstitutionInfo> getAllInstitution() {
        return institutionService.getAllInstitutionInfo();
    }

    @GetMapping("getAllTeachers")
    @ApiOperation("获取所有教师信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "当前页数", defaultValue = "1"),
            @ApiImplicitParam(name = "size", value = "每页数量大小", defaultValue = "10")
    })
    public IPage<Teacher> getAllTeachers(@RequestParam(value = "page", defaultValue = "1") Integer pageNum,
                                         @RequestParam(value = "size", defaultValue = "10") Integer size) {
        QueryWrapper<Teacher> queryWrapper = new QueryWrapper<>();
        queryWrapper.select(Teacher.class,tableFieldInfo -> !"password".equals(tableFieldInfo.getColumn()));
        Page<Teacher> page = new Page<>(pageNum, size);
        return teacherService.getBaseMapper().selectPage(page, queryWrapper);
    }
    @PostMapping("addNode")
    @ApiOperation("添加机构")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type",value = "添加类型（0：学院，1：机构，2：班级）",required = true),
            @ApiImplicitParam(name = "id",value = "所属父id",required = true),
            @ApiImplicitParam(name = "childName",value = "要添加的id",required = true),
            @ApiImplicitParam(name = "classNo",value = "班级编号"),
    })
    public int addNode(@RequestParam("type") int type,
                           @RequestParam("id") int parentId,
                           @RequestParam("childName") String childName,
                           @RequestParam(value = "classNo", required = false) String classNo){
        if(type==0){
            //添加学院
            School school = new School();
            school.setSchoolName(childName);
            schoolService.save(school);
            return school.getSchoolCode();
        } else if(type == 1){
            //添加学院下属机构
            Department department = new Department();
            department.setDepartmentName(childName);
            department.setSchoolCode(parentId);
            departmentService.save(department);
            return department.getDepartmentId();
        } else if(type == 2){
            //添加班级
            ClassInfo classInfo = new ClassInfo();
            classInfo.setClassName(childName);
            classInfo.setDepartmentId(parentId);
            classInfo.setClassNo(classNo);
            classInfoService.save(classInfo);
            return classInfo.getClassId();
        }
        return -1;
    }
    @PostMapping("updateNode")
    @ApiOperation("更新节点信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",value = "修改的id",required = true),
            @ApiImplicitParam(name = "type",value = "修改的类型（1：学院，2：部门，3：班级）",required = true),
            @ApiImplicitParam(name = "name",value = "修改的名称",required = true),
            @ApiImplicitParam(name = "classNo",value = "修改的班级编号",required = false),
            @ApiImplicitParam(name = "description",value = "描述",required = false),
            @ApiImplicitParam(name = "headTeacher",value = "班主任",required = false)

    })
    public Result updateNode(@RequestParam("id") int id,
                              @RequestParam("type") int type,
                              @RequestParam("name") String name,
                              @RequestParam(value = "description",required = false) String description,
                              @RequestParam(value = "headTeacher",required = false) String headTeacher,
                              @RequestParam(value = "classNo", required = false) String classNo){

        if(type==1){
            //更新学院信息
            School school = new School();
            school.setSchoolName(name);
            school.setSchoolCode(id);
            return schoolService.updateById(school)?Result.success():Result.failure(ResultCode.UPDATE_FAILURE);
        } else if(type == 2){
            //更新部门信息
            Department department = new Department();
            department.setDepartmentId(id);
            department.setDepartmentName(name);
            if (description != null) {
                department.setDepartmentDescription(description);
            }
            return departmentService.updateById(department)?Result.success():Result.failure(ResultCode.UPDATE_FAILURE);
        } else if(type==3){
            //更新班级信息
            ClassInfo classInfo = new ClassInfo();
            classInfo.setClassId(id);
            classInfo.setClassName(name);
            classInfo.setClassNo(classNo);
            if (headTeacher != null) {
                try {
                    classInfo.setTeacher(
                            teacherService.getOne(
                                    new QueryWrapper<Teacher>()
                                            .eq("teacher_name",headTeacher))
                                    .getTeacherNo());
                }catch (Exception e){
                    e.printStackTrace();
                    return new Result(29999,"教师信息不正确");
                }
            }
            return classInfoService.updateById(classInfo)?Result.success():Result.failure(ResultCode.UPDATE_FAILURE);
        }
        return null;
    }


}
