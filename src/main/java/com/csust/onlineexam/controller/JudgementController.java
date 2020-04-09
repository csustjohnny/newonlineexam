package com.csust.onlineexam.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.csust.onlineexam.dto.PaginationDTO;
import com.csust.onlineexam.entity.Judgement;
import com.csust.onlineexam.service.impl.JudgementServiceImpl;
import io.swagger.annotations.Api;
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
 * @since 2020-04-09
 */
@RestController
@RequestMapping("/question/judgement")
@Api(tags = "判断题相关接口")
public class JudgementController {
    private final JudgementServiceImpl judgementService;


    public JudgementController(JudgementServiceImpl judgementService) {
        this.judgementService = judgementService;
    }

    public PaginationDTO getAllJudementQuestions(@RequestParam(name = "current", defaultValue = "1") int current,
                                                       @RequestParam(name = "limit", defaultValue = "15") int limit,
                                                       @RequestParam(name = "title", required = false) String title,
                                                       @RequestParam(name = "knowledgePoint", required = false) String knowledgePoint){
        QueryWrapper<Judgement> queryWrapper = new QueryWrapper<>();
        if(title!=null && !"".equals(title)){
            queryWrapper.like("title",title);
        }
        if (knowledgePoint != null && !"".equals(knowledgePoint)) {
            queryWrapper.like("knowledge_point", knowledgePoint);
        }
        Page<Judgement> judgementPage = judgementService.page(new Page<>(current,limit),queryWrapper);
        return new PaginationDTO(0,judgementPage.getRecords(),judgementPage.getTotal());

    }
}
