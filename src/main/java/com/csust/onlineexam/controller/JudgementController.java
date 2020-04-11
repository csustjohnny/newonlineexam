package com.csust.onlineexam.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.csust.onlineexam.constant.Constant;
import com.csust.onlineexam.dto.PaginationDTO;
import com.csust.onlineexam.dto.ResultCode;
import com.csust.onlineexam.entity.Judgement;
import com.csust.onlineexam.dto.Result;
import com.csust.onlineexam.service.impl.JudgementServiceImpl;
import com.csust.onlineexam.service.impl.SubjectServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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

import static com.csust.onlineexam.util.FileRelate.getTemplate;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author johnNick
 * @since 2020-04-09
 */
@RestController
@RequestMapping("/question/judgement")
@Api(tags = "判断题相关接口")
public class JudgementController {
    private final JudgementServiceImpl judgementService;
    private final SubjectServiceImpl subjectService;

    public JudgementController(JudgementServiceImpl judgementService, SubjectServiceImpl subjectService) {
        this.judgementService = judgementService;
        this.subjectService = subjectService;
    }

    @GetMapping("addJudgementQuestionView")
    @ApiOperation("判断题添加界面")
    public ModelAndView judgementAddView() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/question/judgementQuestion/add_judgement_question");
        return modelAndView;
    }

    @GetMapping("editChoiceQuestionView")
    @ApiOperation("判断题编辑界面")
    public ModelAndView judgementEditView() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/question/judgementQuestion/edit_judgement_question");
        return modelAndView;
    }

    @GetMapping("showOneJudgementQuestion")
    @ApiOperation("判断题预览界面")
    public ModelAndView judgementReView() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/question/judgementQuestion/judgementQuestionView");
        return modelAndView;
    }

    @GetMapping("showQuestionBatchAddView")
    @ApiOperation("判断题批量添加界面")
    public ModelAndView judgementBatchAddView() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/question/judgementQuestion/judgement_question_batch_add");
        return modelAndView;
    }

    @GetMapping("downloadTemplate")
    @ApiOperation("下载用户导入信息模板")
    public ResponseEntity<byte[]> downloadTemplate() throws IOException {
        String templatePath = "src/main/resources/static/other/";
        String template = "judgementQuestionTemplate.xlsx";
        return getTemplate(templatePath, template);
    }
    @GetMapping("getAllJudgementQuestions")
    @ApiOperation("获取所有判断题")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", value = "当前页", defaultValue = "1"),
            @ApiImplicitParam(name = "limit", value = "每页数量", defaultValue = "10"),
            @ApiImplicitParam(name = "title", value = "题目"),
            @ApiImplicitParam(name = "knowledgePoint", value = "知识点"),
    })
    public PaginationDTO getAllJudgementQuestions(@RequestParam(name = "current", defaultValue = "1") int current,
                                                  @RequestParam(name = "limit", defaultValue = "15") int limit,
                                                  @RequestParam(name = "title", required = false) String title,
                                                  @RequestParam(name = "knowledgePoint", required = false) String knowledgePoint) {
        QueryWrapper<Judgement> queryWrapper = new QueryWrapper<>();
        if (title != null && !"".equals(title)) {
            queryWrapper.like("title", title);
        }
        if (knowledgePoint != null && !"".equals(knowledgePoint)) {
            queryWrapper.like("knowledge_point", knowledgePoint);
        }
        Page<Judgement> judgementPage = judgementService.page(new Page<>(current, limit), queryWrapper);
        return new PaginationDTO(0, judgementPage.getRecords(), judgementPage.getTotal());
    }

    @PostMapping("/addBatchJudgementQuestions")
    @ApiOperation("批量添加判断题")
    @Transactional(rollbackFor = Exception.class)
    public Result addBatchJudgementQuestions(HttpServletRequest request) throws IOException {
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
        Sheet sheet = workbook.getSheetAt(0);
        DataFormatter formatter = new DataFormatter();
        Map<String, Integer> subjectList = new HashMap<>(50);
        subjectService.list().forEach(subject -> subjectList.put(subject.getCourseName(), subject.getSubjectId()));
        for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
            Row row = sheet.getRow(i);
            Judgement judgement = new Judgement();
            judgement.setTitle(formatter.formatCellValue(row.getCell(0)).trim());
            judgement.setAnswer("对".equals(formatter.formatCellValue(row.getCell(1)).trim()));
            judgement.setKnowledgePoint(formatter.formatCellValue(row.getCell(2)).trim());
            judgement.setLevel((int) row.getCell(3).getNumericCellValue());
            judgement.setSubjectSubordinate(subjectList.get(formatter.formatCellValue(row.getCell(4)).trim()));
            judgement.setAnalysis(formatter.formatCellValue(row.getCell(5)).trim());
            judgementService.save(judgement);
        }
        fis.close();
        return Result.success();
    }

    @PostMapping("deleteOneJudgementQuestion")
    @ApiOperation("删除一道判断题")
    @ApiImplicitParam(name = "judgementId", value = "题目id")
    public Result deleteOneJudgementQuestion(@RequestParam int judgementId) {
        return judgementService.removeById(judgementId) ? Result.success() : Result.failure(ResultCode.DELETE_FAILURE);
    }

    @PostMapping("deleteBatchJudgementQuestions")
    @ApiOperation("批量删除判断题")
    @ApiImplicitParam(name = "judgementList", value = "判断题列表")
    public Result deleteBatchJudgementQuestions(@RequestBody List<Judgement> judgementList) {
        if (judgementList.size() == 0) {
            return Result.failure(ResultCode.PARAMS_INCORRECT);
        }
        List<Integer> idList = new ArrayList<>();
        judgementList.forEach(judgement -> idList.add(judgement.getId()));
        return judgementService.removeByIds(idList) ? Result.success() : Result.failure(ResultCode.DELETE_FAILURE);
    }

    @PostMapping("saveOrUpdateOneJudgementQuestion")
    @ApiOperation("新增或更新问题")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "judgement", value = "题目"),
            @ApiImplicitParam(name = "type", value = "新增或更改（0：新增，1：更新）")
    })
    public Result updateJudgementQuestion(@RequestBody Judgement judgement,
                                          @RequestParam int type) {
        boolean isParamsCorrect = (type == 1 && judgement.getId() == null || "".equals(judgement.getTitle()) || judgement.getTitle() == null);
        if (isParamsCorrect) {
            return Result.failure(ResultCode.PARAMS_INCORRECT);
        }
        return judgementService.saveOrUpdate(judgement) ? Result.success() : Result.failure(ResultCode.UPDATE_FAILURE);
    }
}
