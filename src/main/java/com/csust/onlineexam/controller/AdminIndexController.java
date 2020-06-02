package com.csust.onlineexam.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.csust.onlineexam.dto.Result;
import com.csust.onlineexam.dto.ResultCode;
import com.csust.onlineexam.entity.Index;
import com.csust.onlineexam.entity.Subject;
import com.csust.onlineexam.service.impl.IndexServiceImpl;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author johnNick
 * @since 2020-05-26
 */
@RestController
@RequestMapping("admin/index")
public class AdminIndexController {

    private final IndexServiceImpl indexService;

    public AdminIndexController(IndexServiceImpl indexService) {
        this.indexService = indexService;
    }

    @GetMapping("getAllIndexs")
    @ApiOperation("获取所有指标点")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "当前页数", defaultValue = "1"),
            @ApiImplicitParam(name = "keywords", value = "关键词"),
            @ApiImplicitParam(name = "limit", value = "每页数量大小", defaultValue = "10")
    })
    public Map<String, Object> getAllSubjects(@RequestParam(name = "page", defaultValue = "1") int pageNum,
                                              @RequestParam(name = "limit", defaultValue = "10") int limit,
                                              @RequestParam(name = "keywords", required = false) String keywords) {
        QueryWrapper<Index> queryWrapper = new QueryWrapper<>();
        if (keywords != null && !"".equals(keywords)) {
            queryWrapper.like("index_name", keywords);
        }
        Page<Index> page = new Page<>(pageNum, limit);
        Map<String, Object> result;
        result = new HashMap<>(5);
        result.put("code", 0);
        Page<Index> studentPage = indexService.page(page, queryWrapper);
        result.put("data", studentPage.getRecords());
        result.put("count", studentPage.getTotal());
        return result;
    }
    @PostMapping("addOneIndex")
    public Result addOneIndex(@RequestBody Index index){
        return indexService.save(index)?Result.success():Result.failure(ResultCode.INSERT_FAILURE);
    }

}
