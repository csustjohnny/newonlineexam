package com.csust.onlineexam.service.impl;

import com.csust.onlineexam.entity.Admin;
import com.csust.onlineexam.mapper.AdminMapper;
import com.csust.onlineexam.service.IAdminService;
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
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements IAdminService {

}
