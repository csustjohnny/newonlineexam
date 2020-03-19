package com.csust.onlineexam.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.csust.onlineexam.entity.Student;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author johnNick
 * @since 2020-03-18
 */
public interface StudentMapper extends BaseMapper<Student> {
    /**
     * 用于返回学生详细信息
     * @param page 分页
     * @param wrapper 查询条件
     * @return 查询结果
     */

    List<Map<String,Object>> getStudentInfoList(Page<Map<String,Object>> page, @Param(Constants.WRAPPER) Wrapper<Student> wrapper);
}
