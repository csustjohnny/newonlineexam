package com.csust.onlineexam.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.csust.onlineexam.constant.Constant;
import com.csust.onlineexam.dto.*;
import com.csust.onlineexam.entity.ChoiceQuestion;
import com.csust.onlineexam.entity.FillingInTheBlankQuestion;
import com.csust.onlineexam.entity.Subject;
import com.csust.onlineexam.service.impl.FillingInTheBlankQuestionServiceImpl;
import com.csust.onlineexam.service.impl.SubjectServiceImpl;
import com.csust.onlineexam.util.DgbSecurityUserHelper;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.csust.onlineexam.util.FileRelate.getTemplate;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author johnNick
 * @since 2020-04-12
 */
@RestController
@RequestMapping("/question/filling-in-the-blank-question")
public class FillingInTheBlankQuestionController {
    private final FillingInTheBlankQuestionServiceImpl fillingInTheBlankQuestionService;
    private final SubjectServiceImpl subjectService;


    public FillingInTheBlankQuestionController(FillingInTheBlankQuestionServiceImpl fillingInTheBlankQuestionService, SubjectServiceImpl subjectService) {
        this.fillingInTheBlankQuestionService = fillingInTheBlankQuestionService;
        this.subjectService = subjectService;
    }
    @GetMapping("downloadTemplate")
    @ApiOperation("下载判断题导入信息模板")
    public ResponseEntity<byte[]> downloadTemplate() throws IOException {
        String templatePath = "src/main/resources/static/other/";
        String template = "fillingBlankTemplate.xlsx";
        return getTemplate(templatePath, template);
    }
    @GetMapping("showOneFillingBlankQuestion")
    @ApiOperation("预览填空题界面")
    public ModelAndView showQuestionView() {
        ModelAndView questionView = new ModelAndView();
        questionView.setViewName("question/fillingBlankQuestion/fillingBlankQuestionView");
        return questionView;
    }
    @GetMapping("showQuestionBatchAddView")
    @ApiOperation("显示填空题批量上传界面")
    public ModelAndView showChoiceQuestionBatchAdd() {
        ModelAndView questionAddView = new ModelAndView();
        questionAddView.setViewName("question/fillingBlankQuestion/filling_blank_question_batch_add");
        return questionAddView;
    }
    @GetMapping("editFillingBlankQuestion")
    @ApiOperation("显示填空题编辑界面")
    public ModelAndView editChoiceQuestion() {
        ModelAndView questionEditView = new ModelAndView();
        questionEditView.setViewName("question/fillingBlankQuestion/edit_filling_blank_question");
        return questionEditView;
    }
    @GetMapping("addFillingBlankQuestionView")
    @ApiOperation("显示填空题添加界面")
    public ModelAndView addChoiceQuestion() {
        ModelAndView questionAddView = new ModelAndView();
        questionAddView.setViewName("question/fillingBlankQuestion/add_filling_blank_question");
        return questionAddView;
    }
    @GetMapping("getAllFillingBlankQuestions")
    @ApiOperation("获取所有的填空题")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", value = "当前页", defaultValue = "1"),
            @ApiImplicitParam(name = "limit", value = "每页数量", defaultValue = "10"),
            @ApiImplicitParam(name = "title", value = "题目"),
            @ApiImplicitParam(name = "onlyShowMyQuestion", value = "是否只查看自己的题目"),
            @ApiImplicitParam(name = "knowledgePoint", value = "知识点"),
    })
    public PaginationDTO getAllFillingBlankQuestions(@RequestParam(name = "current", defaultValue = "1") int current,
                                                     @RequestParam(name = "limit", defaultValue = "15") int limit,
                                                     @RequestParam(name = "title", required = false) String title,
                                                     @RequestParam(name = "onlyShowMyQuestion", required = false) boolean onlyShowMyQuestion,
                                                     @RequestParam(name = "knowledgePoint", required = false) String knowledgePoint) {
        QueryWrapper<FillingInTheBlankQuestion> queryWrapper = new QueryWrapper<>();
        if(DgbSecurityUserHelper.isCurrentUserRoleTeacher() && onlyShowMyQuestion){
            queryWrapper.eq("create_teacher",DgbSecurityUserHelper.getCurrentUser().getUsername().split(" ")[1]);
        }
        if (title != null && !"".equals(title)) {
            queryWrapper.like("question", title);
        }
        if (knowledgePoint != null && !"".equals(knowledgePoint)) {
            queryWrapper.like("knowledge_point", knowledgePoint);
        }
        //从数据库中读取学科列表
        Map<Integer, String> subjectList = subjectService.list()
                .stream().collect(Collectors.toMap(Subject::getSubjectId, Subject::getCourseName));
        Page<FillingInTheBlankQuestion> fillingInTheBlankQuestionPage = fillingInTheBlankQuestionService.page(new Page<>(current, limit), queryWrapper);
        List<FillingBlankQuestionDTO> records = fillingInTheBlankQuestionPage.getRecords().stream().map(fillingInTheBlankQuestion -> {
            FillingBlankQuestionDTO fillingBlankQuestionDTO = new FillingBlankQuestionDTO();
            BeanUtils.copyProperties(fillingInTheBlankQuestion, fillingBlankQuestionDTO);
            fillingBlankQuestionDTO.setSubjectName(subjectList.get(fillingInTheBlankQuestion.getSubjectSubordinate()));
            return fillingBlankQuestionDTO;
        }).collect(Collectors.toList());
        return new PaginationDTO(0, records, fillingInTheBlankQuestionPage.getTotal());
    }
    @PostMapping("/addBatchFillingBlankQuestions")
    @ApiOperation("批量添加填空题")
    @Transactional(rollbackFor = Exception.class)
    public Result addBatchChoiceQuestions(HttpServletRequest request) throws IOException {
        MultipartFile multiFile = ((MultipartHttpServletRequest) request).getFile("file");
        assert multiFile != null;
        String fileName = multiFile.getOriginalFilename();
        InputStream fis = multiFile.getInputStream();
        Workbook workbook;
        assert fileName != null;
        if (fileName.endsWith(Constant.XLSX_SUFFIX)) {
            workbook = new XSSFWorkbook(fis);
        } else {
            workbook = new HSSFWorkbook(fis);
        }
        String teacherNo = null;
        if(DgbSecurityUserHelper.getRoleList().contains(Constant.ROLE_TEACHER)){
            teacherNo = DgbSecurityUserHelper.getCurrentUser().getUsername().split(" ")[1];
        }
        Sheet sheet = workbook.getSheetAt(0);
        DataFormatter formatter = new DataFormatter();
        Map<String, Integer> subjectList = new HashMap<>(20);
        subjectService.list().forEach(subject -> subjectList.put(subject.getCourseName(), subject.getSubjectId()));
        for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
            Row row = sheet.getRow(i);
            if("".equals(formatter.formatCellValue(row.getCell(0)).trim())){
                break;
            }
            FillingInTheBlankQuestion question = new FillingInTheBlankQuestion();
            question.setQuestion(formatter.formatCellValue(row.getCell(0)).trim());
            question.setBlankOne(formatter.formatCellValue(row.getCell(1)).trim());
            question.setBlankTwo(formatter.formatCellValue(row.getCell(2)).trim());
            question.setBlankThree(formatter.formatCellValue(row.getCell(3)).trim());
            question.setBlankFour(formatter.formatCellValue(row.getCell(4)).trim());
            question.setBlankFive(formatter.formatCellValue(row.getCell(5)).trim());
            question.setKnowledgePoint(formatter.formatCellValue(row.getCell(6)).trim());
            if (!"".equals(formatter.formatCellValue(row.getCell(10)))) {
                question.setLevel((int) row.getCell(7).getNumericCellValue());
            }
            question.setSubjectSubordinate(subjectList.get(formatter.formatCellValue(row.getCell(8)).trim()));
            question.setBlankCount((int) row.getCell(9).getNumericCellValue());
            question.setCreateTeacher(teacherNo);
            fillingInTheBlankQuestionService.save(question);
        }
        fis.close();
        return Result.success();
    }
    @PostMapping("deleteBatchQuestions")
    @Transactional(rollbackFor = Exception.class)
    @ApiOperation("批量删除填空题题")
    @ApiImplicitParam(name = "questionList", value = "问题列表")
    public Result deleteBatchChoiceQuestion(@RequestBody List<FillingInTheBlankQuestion> questionList) {
        if (questionList.size() == 0) {
            return Result.failure(ResultCode.PARAMS_INCORRECT);
        }
        //教师角色判断是否拥有权限
        if(DgbSecurityUserHelper.getRoleList().contains(Constant.ROLE_TEACHER)){
            String currentTeacherNo = DgbSecurityUserHelper.getCurrentUser().getUsername().split(" ")[1];
            for(FillingInTheBlankQuestion question : questionList){
                if(!currentTeacherNo.equals(question.getCreateTeacher())){
                    return Result.failure(ResultCode.AUTHORITY_ERROR);
                }
            }
        }
        List<Integer> idList = new ArrayList<>();
        questionList.forEach(fillingInTheBlankQuestion -> idList.add(fillingInTheBlankQuestion.getQuestionId()));
        fillingInTheBlankQuestionService.removeByIds(idList);
        return Result.success();
    }
    @PostMapping("deleteOneQuestion")
    @ApiOperation("删除一个填空题")
    @ApiImplicitParam(name = "id", value = "问题ID", required = true)
    public Result deleteOneQuestion(@RequestParam int id) {
        QueryWrapper<FillingInTheBlankQuestion> questionQueryWrapper = new QueryWrapper<>();
        if(DgbSecurityUserHelper.getRoleList().contains(Constant.ROLE_TEACHER)){
            questionQueryWrapper.eq("create_teacher",DgbSecurityUserHelper.getCurrentUser().getUsername().split(" ")[1]);
        }
        questionQueryWrapper.eq("id",id);
        return fillingInTheBlankQuestionService.remove(questionQueryWrapper) ? Result.success() : Result.failure(ResultCode.DELETE_FAILURE);
    }
    @PostMapping("saveOrUpdateOneQuestion")
    @ApiOperation("更新或添加一道填空题")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "fillingInTheBlankQuestion", value = "要更新的填空题"),
            @ApiImplicitParam(name = "type", value = "新增或更改（0：新增，1：更新）")
    })
    public Result updateOneChoiceQuestion(@RequestBody FillingInTheBlankQuestion fillingInTheBlankQuestion,
                                          @RequestParam int type) {
        if (type == 1 && fillingInTheBlankQuestion.getQuestionId() == null) {
            return Result.failure(ResultCode.PARAMS_INCORRECT);
        }
        boolean result;
        //教师添加或更新问题
        if(DgbSecurityUserHelper.getRoleList().contains(Constant.ROLE_TEACHER)){
            String currentTeacherNo = DgbSecurityUserHelper.getCurrentUser().getUsername().split(" ")[1];
            String createTeacherNo = fillingInTheBlankQuestionService.getById(fillingInTheBlankQuestion.getQuestionId()).getCreateTeacher();
            //是更新自己的问题
            fillingInTheBlankQuestion.setCreateTeacher(currentTeacherNo);
            if(type==1 && currentTeacherNo.equals(createTeacherNo)) {
                result = fillingInTheBlankQuestionService.updateById(fillingInTheBlankQuestion);
            } else {
                result = fillingInTheBlankQuestionService.save(fillingInTheBlankQuestion);
            }
        } else{
            //管理员更新或添加
            result = fillingInTheBlankQuestionService.saveOrUpdate(fillingInTheBlankQuestion);
        }
        return result ? Result.success() : Result.failure(ResultCode.UPDATE_FAILURE);
    }
}
