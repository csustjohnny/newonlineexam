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
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
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
        ModelAndView adminWelcome = new  ModelAndView();
        adminWelcome.setViewName("admin_welcome");
        return adminWelcome;
    }
    @GetMapping("student_batch_add")
    @ApiOperation("学生批量导入界面")
    public ModelAndView studentBatchAdd() {
        ModelAndView studentWelcome = new ModelAndView();
        studentWelcome.setViewName("admin/student/student_add");
        return studentWelcome;
    }
    @GetMapping("add_student_table")
    @ApiOperation("学生单个添加界面")
    public ModelAndView studentSingleAdd() {
        ModelAndView studentWelcome = new ModelAndView();
        studentWelcome.setViewName("admin/table/add_student");
        return studentWelcome;
    }
    @GetMapping("edit_student_table")
    @ApiOperation("编辑单个学生界面")
    public ModelAndView studentSingleEdit(){
        ModelAndView studentEditTable = new ModelAndView();
        studentEditTable.setViewName("admin/table/edit_student");
        return studentEditTable;
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
    @GetMapping("teacher_add")
    @ApiOperation("教师添加界面")
    public ModelAndView teacherAdd() {
        ModelAndView teacherAdd = new ModelAndView();
        teacherAdd.setViewName("admin/teacher/teacher_add");
        return teacherAdd;
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
            @ApiImplicitParam(name = "schoolName", value = "所属学院"),
            @ApiImplicitParam(name = "className", value = "所属班级名称"),
            @ApiImplicitParam(name = "studentName", value = "姓名"),
            @ApiImplicitParam(name = "limit", value = "每页数量大小", defaultValue = "10")
    })
    public Map<String,Object> getAllStudents(@RequestParam(value = "page", defaultValue = "1") Integer pageNum,
                                        @RequestParam(value = "limit", defaultValue = "10") Integer size,
                                        @RequestParam(value = "className", required = false) String className,
                                        @RequestParam(value = "studentName", required = false) String studentName,
                                        @RequestParam(value = "schoolName", required = false) String schoolName) {
        QueryWrapper<Student> queryWrapper = new QueryWrapper<>();
        if(studentName!=null && !"".equals(studentName)){
            queryWrapper.like("name",studentName);
        }
        if(className!=null && !"".equals(className)){
            queryWrapper.eq("class_name",className);
        }
        if (schoolName != null && !"".equals(schoolName)) {
            queryWrapper.eq("school_name",schoolName);
        }
        Page<Map<String,Object>> page = new Page<>(pageNum, size);
        Map<String, Object> result = new HashMap<>(2);
        result.put("code",0);
        Page<Map<String,Object>> studentPage = studentService.getStudentInfoList(page, queryWrapper);
        result.put("data",studentPage.getRecords());
        result.put("count",studentPage.getTotal());
        return result;
    }
    @ApiOperation("获取班级信息")
    @GetMapping("/getClassInfoList")
    public List<ClassInfo> getClassInfoList(){
        return classInfoService.list();
    }
    @GetMapping("/downloadTemplate/{fileType}")
    @ApiOperation("下载用户导入信息模板")
    @ApiImplicitParam(name = "fileType",value = "下载模板类型",defaultValue = "",required = true)
    public ResponseEntity<byte[]> downloadTemplate(@PathVariable("fileType") int type) throws IOException {
        String templatePath = "src/main/resources/static/other/";
        String template;
        switch (type) {
            case 1:
                template = "studentTemplate.xls";
                break;
            case 2:
                template = "teacherTemplate.xls";
                break;
            case 3:
                template = "adminTemplate.xls";
                break;
            default:
                template = null;
                break;
        }
        if (template == null) {
            return null;
        }
        String fileName = templatePath + template;
        File file = new File(fileName);
        if (!file.exists()) {
            System.out.println(file.createNewFile());
        }
        byte[] body;
        InputStream is = new FileInputStream(file);
        body = new byte[is.available()];
        System.out.println(is.read(body));
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attchement;filename=" + file.getName());
        HttpStatus statusCode = HttpStatus.OK;
        return new ResponseEntity<>(body, headers, statusCode);
    }
    /**
     * 上传文件
     *
     * @param request 获取传输文件
     * @return 传输结果
     */
    @PostMapping("/uploadTemplate/{type}")
    @ResponseBody
    @Transactional(rollbackFor = Exception.class)
    @ApiOperation("上传文件接口")
    public Result uploadTemplate(HttpServletRequest request, @PathVariable int type) throws IOException {

        MultipartHttpServletRequest multipartHttpServletRequest =
                (MultipartHttpServletRequest) request;
        // 获取上传的文件
        Map<String, String> message = new HashMap<>(1);
        MultipartFile multiFile = multipartHttpServletRequest.getFile("file");
        assert multiFile != null;
        String fileName = multiFile.getOriginalFilename();
        assert fileName != null;
        InputStream fis = multiFile.getInputStream();
        Workbook workbook = null;
        System.out.println("xlsx".equals(fileName.split("\\.")[1]));
        if("xlsx".equals(fileName.split("\\.")[1])) {
            workbook = new XSSFWorkbook(fis);
        } else {
            workbook = new HSSFWorkbook(fis);
        }
        Sheet sheet = workbook.getSheetAt(0);
        DataFormatter formatter = new DataFormatter();
        if(type==1) {
            //批量插入学生信息
            Map<String, Integer> classList = new HashMap<>(20);
            List<ClassInfo> list = classInfoService.list();
            for (ClassInfo classInfo : list) {
                classList.put(classInfo.getClassName(), classInfo.getClassId());
            }

            for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
                Row row = sheet.getRow(i);
                Student student = new Student();
                student.setStudentNo(row.getCell(0).getStringCellValue().trim());
                student.setName(row.getCell(1).getStringCellValue().trim());
                student.setSex(row.getCell(2).getStringCellValue().trim());
                student.setPhone(formatter.formatCellValue(row.getCell(3)).trim());
                student.setClassId(classList.get(row.getCell(4).getStringCellValue().trim()));
                student.setQq(formatter.formatCellValue(row.getCell(5)).trim());
                student.setWechat(formatter.formatCellValue(row.getCell(6)).trim());
                System.out.println(row.getPhysicalNumberOfCells());
                if (row.getPhysicalNumberOfCells() < 8) {
                    student.setPassword(new BCryptPasswordEncoder().encode(row.getCell(0).getStringCellValue().trim()));
                } else {
                    student.setPassword(new BCryptPasswordEncoder().encode(formatter.formatCellValue(row.getCell(7)).trim()));
                }
                studentService.saveOrUpdate(student);

            }
        } else if(type==2){
            //批量插入教师信息
            for(int i=1;i < sheet.getPhysicalNumberOfRows(); i++){
                Row row = sheet.getRow(i);
                if(row.getCell(0)==null ||  "".equals(row.getCell(0).getStringCellValue())){
                    break;
                }
                Teacher teacher = new Teacher();
                teacher.setTeacherNo(formatter.formatCellValue(row.getCell(0)).trim());
                teacher.setTeacherName(row.getCell(1).getStringCellValue());
                teacher.setPhone(formatter.formatCellValue(row.getCell(2)).trim());
                teacher.setEmail(row.getCell(3).getStringCellValue());
                teacher.setQq(formatter.formatCellValue(row.getCell(4)).trim());
                teacher.setWechat(formatter.formatCellValue(row.getCell(5)).trim());
                if(row.getPhysicalNumberOfCells() < 7){
                    teacher.setPassword(new BCryptPasswordEncoder().encode(row.getCell(0).getStringCellValue()));
                } else {
                    teacher.setPassword(new BCryptPasswordEncoder().encode(row.getCell(6).getStringCellValue()));
                }
                teacherService.saveOrUpdate(teacher);
            }
        }
        fis.close();
        return Result.success();
    }
    @PostMapping("/deleteBatchStudents")
    @ApiOperation("批量删除学生")
    @ApiImplicitParam(name = "studentList",value = "要删除的学生列表")
    @Transactional(rollbackFor = Exception.class)
    public Result deleteBatchStudents(@RequestBody List<Student> studentList){
        if(studentList.size()==0){
            return Result.failure(ResultCode.PARAMS_INCORRECT);
        }
        List<String> idList = new ArrayList<>();
        studentList.forEach(student -> idList.add(student.getStudentNo()));
        studentService.removeByIds(idList);
        return Result.success();
    }
    @PostMapping("/deleteOneStudent")
    @ApiOperation("删除单个学生")
    @ApiImplicitParam(name = "studentNo",value = "学号",required = true)
    public Result deleteOnStudent(@RequestParam(name = "studentNo") String studentNo){
        return studentService.removeById(studentNo)?Result.success():Result.failure(ResultCode.DELETE_FAILURE);
    }
    @PostMapping("saveOrUpdateOneStudent")
    @ApiOperation("添加或更新单个学生信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "student", value = "学生信息",required = true),
            @ApiImplicitParam(name = "type", value = "类型(0添加，1更新)",required = true),
    })
    public Result addOneStudent(@RequestBody Student student,
                                @RequestParam int type){
        //添加已有学号返回错误信息
        if(type==0 && studentService.getById(student.getStudentNo())!=null){
            return Result.failure(ResultCode.INSERT_DUPLICATE);
        }
        if (student.getPassword()==null || "".equals(student.getPassword())){
            student.setPassword(new BCryptPasswordEncoder().encode(student.getStudentNo()));
        } else {
            System.out.println(student.getPassword());
            student.setPassword(new BCryptPasswordEncoder().encode(student.getPassword()));
        }
        try{
            studentService.saveOrUpdate(student);
        }catch(Exception e){
            e.printStackTrace();
            return Result.failure(ResultCode.INSERT_FAILURE);
        }
        return Result.success();
    }
    @GetMapping("getAllInstitution")
    @ApiOperation("获取所有机构信息")
    public List<InstitutionInfo> getAllInstitution() {
        return institutionService.getAllInstitutionInfo();
    }

    @GetMapping("getAllTeachers")
    @ApiOperation("获取所有教师信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "当前页数", defaultValue = "1",required = true),
            @ApiImplicitParam(name = "size", value = "每页数量大小", defaultValue = "10",required = true),
            @ApiImplicitParam(name = "teacherName", value = "姓名")
    })
    public IPage<Teacher> getAllTeachers(@RequestParam(value = "page", defaultValue = "1") Integer pageNum,
                                         @RequestParam(value = "size", defaultValue = "10") Integer size,
                                         @RequestParam(value = "teacherName",required = false) String teacherName) {
        QueryWrapper<Teacher> queryWrapper = new QueryWrapper<>();
        if(teacherName!=null && !"".equals(teacherName)){
            queryWrapper.like("teacher_name",teacherName);
        }
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
            @ApiImplicitParam(name = "classNo",value = "修改的班级编号"),
            @ApiImplicitParam(name = "description",value = "描述"),
            @ApiImplicitParam(name = "headTeacher",value = "班主任")

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
