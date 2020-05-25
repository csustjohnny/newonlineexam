package com.csust.onlineexam.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.csust.onlineexam.dto.Result;
import com.csust.onlineexam.entity.TestPaper;
import com.csust.onlineexam.service.impl.TestPaperServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author johnNick
 * @since 2020-05-24
 */
@RestController
@RequestMapping("/test-paper")
public class TestPaperController {

    private final TestPaperServiceImpl testPaperService;

    @Autowired
    public TestPaperController(TestPaperServiceImpl testPaperService) {
        this.testPaperService = testPaperService;
    }

    @GetMapping("test_paper_management")
    public ModelAndView showTestPaper(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/test/test_paper_management");
        return modelAndView;
    }
    @GetMapping("getExamQuestions")
    public Result getTestPaper(@RequestParam Integer examId){
        Result result = Result.success();
        //获取试卷
        List<TestPaper> paperList = testPaperService.list(new QueryWrapper<TestPaper>().eq("exam_id",examId));

        return result;
    }
}
