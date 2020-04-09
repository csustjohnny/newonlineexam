package com.csust.onlineexam.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.csust.onlineexam.constant.Constant;
import com.csust.onlineexam.entity.ChoiceQuestion;
import com.csust.onlineexam.entity.Result;
import com.csust.onlineexam.entity.ResultCode;
import com.csust.onlineexam.service.impl.ChoiceQuestionServiceImpl;
import com.csust.onlineexam.service.impl.SubjectServiceImpl;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
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

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author johnNick
 * @since 2020-04-05
 */
@RestController
@RequestMapping("/question/choice-question")
public class ChoiceQuestionController {
    private final ChoiceQuestionServiceImpl choiceQuestionService;
    private final SubjectServiceImpl subjectService;

    @Autowired
    public ChoiceQuestionController(ChoiceQuestionServiceImpl choiceQuestionService, SubjectServiceImpl subjectService) {
        this.choiceQuestionService = choiceQuestionService;
        this.subjectService = subjectService;
    }

    @GetMapping("showOneChoiceQuestion")
    public ModelAndView showQuestionView(){
        ModelAndView questionView = new ModelAndView();
        questionView.setViewName("choiceQuestionView");
        return  questionView;
    }
    @GetMapping("editChoiceQuestion")
    public ModelAndView editChoiceQuestion(){
        ModelAndView questionEditView = new ModelAndView();
        questionEditView.setViewName("edit_choice_question");
        return  questionEditView;
    }

    @GetMapping("getAllChoiceQuestions")
    @ApiOperation("获取所有的选择题")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", value = "当前页",defaultValue = "1"),
            @ApiImplicitParam(name = "limit", value = "每页数量",defaultValue = "10"),
            @ApiImplicitParam(name = "title", value = "题目"),
            @ApiImplicitParam(name = "knowledgePoint", value = "知识点"),
    })
    public Map<String, Object> getAllChoiceQuestions(@RequestParam(name = "current", defaultValue = "1") int current,
                                                     @RequestParam(name = "limit", defaultValue = "15") int limit,
                                                     @RequestParam(name = "title", required = false) String title,
                                                     @RequestParam(name = "knowledgePoint", required = false) String knowledgePoint) {
        QueryWrapper<ChoiceQuestion> queryWrapper = new QueryWrapper<>();
        if (title != null && !"".equals(title)) {
            queryWrapper.like("title", title);
        }
        if (knowledgePoint != null && !"".equals(knowledgePoint)) {
            queryWrapper.like("knowledge_point", knowledgePoint);
        }
        Page<ChoiceQuestion> choiceQuestionPage = choiceQuestionService.page(new Page<>(current, limit), queryWrapper);
        Map<String, Object> result = new HashMap<>(5);
        result.put("code", 0);
        result.put("data", choiceQuestionPage.getRecords());
        result.put("count", choiceQuestionPage.getTotal());
        return result;
    }

    @PostMapping("/addBatchChoiceQuestions")
    @ApiOperation("批量添加选择题")
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
        Sheet sheet = workbook.getSheetAt( 0);
        DataFormatter formatter = new DataFormatter();
        Map<String,Integer> subjectList = new HashMap<>(50);
        subjectService.list().forEach(subject -> subjectList.put(subject.getCourseName(),subject.getSubjectId()));
        for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
            Row row = sheet.getRow(i);
            ChoiceQuestion choiceQuestion = new ChoiceQuestion();
            choiceQuestion.setTitle(formatter.formatCellValue(row.getCell(0)).trim());
            choiceQuestion.setOptionA(formatter.formatCellValue(row.getCell(1)).trim());
            choiceQuestion.setOptionB(formatter.formatCellValue(row.getCell(2)).trim());
            choiceQuestion.setOptionC(formatter.formatCellValue(row.getCell(3)).trim());
            choiceQuestion.setOptionD(formatter.formatCellValue(row.getCell(4)).trim());
            choiceQuestion.setOptionE(formatter.formatCellValue(row.getCell(5)).trim());
            choiceQuestion.setOptionF(formatter.formatCellValue(row.getCell(6)).trim());
            choiceQuestion.setAnswer(formatter.formatCellValue(row.getCell(7)).trim());
            choiceQuestion.setAnalysis(formatter.formatCellValue(row.getCell(8)).trim());
            choiceQuestion.setKnowledgePoint(formatter.formatCellValue(row.getCell(9)).trim());
            choiceQuestion.setLevel((int) row.getCell(10).getNumericCellValue());
            choiceQuestion.setSubjectSubordinate(subjectList.get(formatter.formatCellValue(row.getCell(11)).trim()));
            choiceQuestion.setIsMultiple("是".equals(formatter.formatCellValue(row.getCell(9)).trim()));
            choiceQuestionService.save(choiceQuestion);
        }
        return Result.success();
    }
    @PostMapping("deleteBatchChoiceQuestion")
    @Transactional(rollbackFor = Exception.class)
    @ApiOperation("批量删除选择题")
    @ApiImplicitParam(name = "questionList", value = "问题列表")
    public Result deleteBatchChoiceQuestion(@RequestBody List<ChoiceQuestion> questionList){
        if(questionList.size()==0){
            return Result.failure(ResultCode.PARAMS_INCORRECT);
        }
        List<Integer> idList = new ArrayList<>();
        questionList.forEach(choiceQuestion -> idList.add(choiceQuestion.getId()));
        choiceQuestionService.removeByIds(idList);
        return Result.success();
    }
    @PostMapping("deleteOneChoiceQuestion")
    @ApiOperation("删除一个选择题")
    @ApiImplicitParam(name = "id",value = "问题ID",required = true)
    public Result deleteOneQuestion(@RequestParam int id){
        choiceQuestionService.removeById(id);
        return Result.success();
    }

}
