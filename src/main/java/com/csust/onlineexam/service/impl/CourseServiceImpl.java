package com.csust.onlineexam.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.csust.onlineexam.entity.Course;
import com.csust.onlineexam.mapper.CourseMapper;
import com.csust.onlineexam.service.ICourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author johnNick
 * @since 2020-05-24
 */
@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements ICourseService {
    @Override
    public Page<Map<String, Object>> getCourseInfoList(Page<Map<String, Object>> page, Wrapper<Course> courseWrapper) {
        return page.setRecords(this.baseMapper.getCourseInfoList(page, courseWrapper));
    }
}
