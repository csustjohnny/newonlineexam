package com.csust.onlineexam.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author Lenovo
 */
public class LoginSuccessHandler implements AuthenticationSuccessHandler {


    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
        String path = httpServletRequest.getContextPath();
        String basePath = httpServletRequest.getScheme() + "://" + httpServletRequest.getServerName()
                + ":" + httpServletRequest.getServerPort() + path + "/";
        String roleAdmin = "ROLE_ADMIN";
        String roleTeacher = "ROLE_TEACHER";
        Map<String,String> map = new HashMap<>();

        System.out.println(httpServletRequest.getParameter("userType"));
        if(roles.contains(roleAdmin)){
            map.put("url","admin/index");
        }else if(roles.contains(roleTeacher)){
            map.put("url","teacher/index");
        }  else {
            map.put("url","student/index");
        }
        httpServletResponse.setContentType("text/json; charset=utf-8");
        ObjectMapper mapper = new ObjectMapper();
        String message = mapper.writeValueAsString(map);
        httpServletResponse.getWriter().write(message);
    }
}
