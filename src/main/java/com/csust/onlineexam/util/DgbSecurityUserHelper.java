package com.csust.onlineexam.util;

import com.csust.onlineexam.constant.Constant;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author ：Lenovo
 * @date ：Created in 2020/4/22 10:47
 * @description : 获取当前登录用户信息类
 * @modified By：
 */
public class DgbSecurityUserHelper {
    /**
     * 获取当前用户信息
     * @return 授权信息
     */
    public static Authentication getCurrentUserAuthentication(){
        return SecurityContextHolder.getContext().getAuthentication();
    }

    /**
     * 获取当前用户
     * @return 当前用户
     */
    public static Object getCurrentPrincipal(){
        return getCurrentUserAuthentication().getPrincipal();
    }

    /**
     * 获取当前登录用户
     * @return 当前登录用户信息
     */
    public static User getCurrentUser(){
        return (User)getCurrentPrincipal();
    }

    public static List<String> getRoleList(){
        return getCurrentUser().getAuthorities().stream()
                .map((authority-> authority.getAuthority().toUpperCase()))
                .collect(Collectors.toList());

    }
    public static boolean isCurrentUserRoleTeacher(){
        return getRoleList().contains(Constant.ROLE_TEACHER);
    }
}
