package com.csust.onlineexam.service.impl;

import com.csust.onlineexam.entity.Department;
import com.csust.onlineexam.mapper.DepartmentMapper;
import com.csust.onlineexam.service.IDepartmentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author johnNick
 * @since 2020-03-18
 */
@Service
public class DepartmentServiceImpl extends ServiceImpl<DepartmentMapper, Department> implements IDepartmentService {

}
