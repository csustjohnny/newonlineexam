package com.csust.onlineexam.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.csust.onlineexam.constant.Constant;
import com.csust.onlineexam.dto.PaginationDTO;
import com.csust.onlineexam.dto.Result;
import com.csust.onlineexam.dto.ResultCode;
import com.csust.onlineexam.entity.CodeQuestion;
import com.csust.onlineexam.service.impl.CodeQuestionServiceImpl;
import com.csust.onlineexam.util.DgbSecurityUserHelper;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author johnNick
 * @since 2020-04-22
 */
@RestController
@RequestMapping("/question/code-question")
public class CodeQuestionController {

    private final CodeQuestionServiceImpl codeQuestionService;

    public CodeQuestionController(CodeQuestionServiceImpl codeQuestionService) {
        this.codeQuestionService = codeQuestionService;
    }

    @GetMapping("/showAddCodeQuestion")
    @ApiOperation("添加单个编程题")
    public ModelAndView addCodeQuestion(){
        ModelAndView questionView = new ModelAndView();
        questionView.setViewName("question/codeQuestion/add_code_question");
        return questionView;
    }
    @GetMapping("/showQuestionBatchAddView")
    public ModelAndView showQuestionBatchAddView() {
        ModelAndView personInfo = new ModelAndView();
        personInfo.setViewName("question/codeQuestion/code_question_batch_add");
        return personInfo;
    }
    @GetMapping("getAllCodeQuestions")
    @ApiOperation("/获取所有题目")
    public PaginationDTO getAllCodeQuestions(@RequestParam(name = "current", defaultValue = "1") int current,
                                             @RequestParam(name = "limit", defaultValue = "15") int limit,
                                             @RequestParam(name = "title", required = false) String title,
                                             @RequestParam(name = "onlyShowMyQuestion", required = false) boolean onlyShowMyQuestion,
                                             @RequestParam(name = "knowledgePoint", required = false) String knowledgePoint){
        QueryWrapper<CodeQuestion> questionQueryWrapper = new QueryWrapper<>();
        if(DgbSecurityUserHelper.isCurrentUserRoleTeacher() && onlyShowMyQuestion){
            questionQueryWrapper.eq("create_teacher",DgbSecurityUserHelper.getCurrentUser().getUsername().split(" ")[1]);
        }
        if(title!=null && !"".equals(title)){
            questionQueryWrapper.like("title",title);
        }
        if(knowledgePoint!=null && !"".equals(knowledgePoint)){
            questionQueryWrapper.like("knowledge_point", knowledgePoint);
        }
        Page<CodeQuestion> codeQuestionPage = codeQuestionService.page(new Page<>(current, limit), questionQueryWrapper);
        List<CodeQuestion> records = codeQuestionPage.getRecords();
        return new PaginationDTO(0, records, codeQuestionPage.getTotal());
    }
    @PostMapping("/saveOrUpdateQuestion")
    public Result addCodeOneQuestion(@RequestBody CodeQuestion codeQuestion,
                                     @RequestParam int type){
        if(type == 1 && codeQuestion.getId()==null){
            return Result.failure(ResultCode.PARAMS_INCORRECT);
        }
        boolean result;
        //教师添加或更新问题
        if(DgbSecurityUserHelper.getRoleList().contains(Constant.ROLE_TEACHER)){
            String currentTeacherNo = DgbSecurityUserHelper.getCurrentUser().getUsername().split(" ")[1];
            String createTeacherNo = codeQuestionService.getById(codeQuestion.getId()).getCreateTeacher();
            //是更新自己的问题
            codeQuestion.setCreateTeacher(currentTeacherNo);
            if(type==1 && currentTeacherNo.equals(createTeacherNo)) {
                result = codeQuestionService.updateById(codeQuestion);
            } else {
                result = codeQuestionService.save(codeQuestion);
            }
        } else{
            //管理员更新或添加
            result = codeQuestionService.saveOrUpdate(codeQuestion);
        }
        System.out.println(codeQuestion);
        return result ?Result.success():Result.failure(ResultCode.INSERT_FAILURE);
    }
    @PostMapping("/addFPSQuestion")
    public Result importQDUOJQuestion(HttpServletRequest request) throws ParserConfigurationException, IOException, SAXException {
        String teacherNo = null;
        if(DgbSecurityUserHelper.getRoleList().contains(Constant.ROLE_TEACHER)){
            teacherNo = DgbSecurityUserHelper.getCurrentUser().getUsername().split(" ")[1];
        }
        MultipartFile multiFile = ((MultipartHttpServletRequest) request).getFile("file");
        DocumentBuilder builder= DocumentBuilderFactory.newInstance().newDocumentBuilder();
        File f = new File("D:/BaiduYunDownload/fps-201616080307-1000.xml");
        assert multiFile != null;
        Document document = builder.parse(multiFile.getInputStream());
        Element root = document.getDocumentElement();
        //获取元素的子元素
        NodeList children = root.getChildNodes();
        CodeQuestion codeQuestion;
        for (int i = 0; i < children.getLength(); i++) {
            Node child = children.item(i);
            //获取问题项目
            if("item".equals(child.getNodeName())){
                codeQuestion = new CodeQuestion();
                codeQuestion.setTitle(((Element) child).getElementsByTagName("title").item(0).getTextContent());
                codeQuestion.setDescription(((Element) child).getElementsByTagName("description").item(0).getTextContent());
                codeQuestion.setInput(((Element) child).getElementsByTagName("input").item(0).getTextContent());
                codeQuestion.setOutput(((Element) child).getElementsByTagName("output").item(0).getTextContent());
                codeQuestion.setSampleInput(((Element) child).getElementsByTagName("sample_input").item(0).getTextContent());
                codeQuestion.setSampleOutput(((Element) child).getElementsByTagName("sample_output").item(0).getTextContent());
                codeQuestion.setTestOutput(((Element) child).getElementsByTagName("test_output").item(0).getTextContent());
                codeQuestion.setTimeLimit(Integer.valueOf(((Element) child).getElementsByTagName("time_limit").item(0).getTextContent()));
                codeQuestion.setMemoryLimit(Integer.valueOf(((Element) child).getElementsByTagName("memory_limit").item(0).getTextContent()));
                codeQuestion.setHint(((Element) child).getElementsByTagName("hint").item(0).getTextContent());
                codeQuestion.setCreateTeacher(teacherNo);
                codeQuestionService.save(codeQuestion);

            }
        }
        return Result.success();
    }

}
