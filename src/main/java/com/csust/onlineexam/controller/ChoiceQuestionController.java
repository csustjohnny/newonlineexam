package com.csust.onlineexam.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.csust.onlineexam.entity.ChoiceQuestion;
import com.csust.onlineexam.service.impl.ChoiceQuestionServiceImpl;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author johnNick
 * @since 2020-04-05
 */
@RestController
@RequestMapping("/choice-question")
public class ChoiceQuestionController {
    private final ChoiceQuestionServiceImpl choiceQuestionService;

    @Autowired
    public ChoiceQuestionController(ChoiceQuestionServiceImpl choiceQuestionService) {
        this.choiceQuestionService = choiceQuestionService;
    }

    @GetMapping("getAllChoiceQuestions")
    @ApiOperation("获取所有的选择题")
    public Map<String,Object> getAllChoiceQuestions(@RequestParam(name = "current", defaultValue = "1") int current,
                                                    @RequestParam(name = "limit",defaultValue = "15") int limit,
                                                    @RequestParam(name = "title", required = false) String title,
                                                    @RequestParam(name = "knowledgePoint", required = false) String knowledgePoint){
        QueryWrapper<ChoiceQuestion> queryWrapper = new QueryWrapper<>();
        if(title!=null && !"".equals(title)){
            queryWrapper.like("title",title);
        }
        if (knowledgePoint != null && !"".equals(knowledgePoint)) {
            queryWrapper.like("knowledge_point",knowledgePoint);
        }
        Page<ChoiceQuestion> choiceQuestionPage = choiceQuestionService.page(new Page<>(current,limit),queryWrapper);
        Map<String,Object> result = new HashMap<>(5);
        result.put("code",0);
        result.put("data",choiceQuestionPage.getRecords());
        result.put("count",choiceQuestionPage.getTotal());
        return result;
    }

}
