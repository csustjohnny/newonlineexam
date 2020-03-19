package com.csust.onlineexam.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.csust.onlineexam.entity.Student;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author johnNick
 * @since 2020-03-18
 */
public interface IStudentService extends IService<Student> {


    /**
     * 获取学生详细信息
     * @param page 分页
     * @param studentWrapper 条件构造器
     * @return 查询结果
     */
    Page<Map<String,Object>> getStudentInfoList(@Param("page") Page<Map<String,Object>> page, Wrapper<Student> studentWrapper);
}
