package com.csust.onlineexam.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.csust.onlineexam.entity.Course;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author johnNick
 * @since 2020-05-24
 */
public interface ICourseService extends IService<Course> {

    /**
     * 获取学生详细信息
     * @param page 分页
     * @param courseWrapper 条件构造器
     * @return 查询结果
     */
    Page<Map<String,Object>> getCourseInfoList(@Param("page") Page<Map<String,Object>> page, Wrapper<Course> courseWrapper);
}
