package com.csust.onlineexam.controller;


import com.csust.onlineexam.entity.Subject;
import com.csust.onlineexam.service.impl.SubjectServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author johnNick
 * @since 2020-04-01
 */
@RestController
@RequestMapping("/subject")
@Api(tags = "学科相关接口")
public class SubjectController {
    private final SubjectServiceImpl subjectService;

    public SubjectController(SubjectServiceImpl subjectService) {
        this.subjectService = subjectService;
    }

    @RequestMapping("getSubjectList")
    @ApiOperation("获取科目列表")
    public List<Subject> getSubjectList(){
        return subjectService.list();
    }
}
