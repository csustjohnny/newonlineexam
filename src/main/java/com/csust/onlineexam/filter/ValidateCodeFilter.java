package com.csust.onlineexam.filter;

import com.csust.onlineexam.constant.Constant;
import com.csust.onlineexam.handler.LoginFailureHandler;
import com.csust.onlineexam.service.impl.UserServiceImpl;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Lenovo
 * @ Date       ：Created in 13:21 2020/2/15
 * @ Description：验证验证码过滤器类
 * @ Modified By：
 */
@Component
public class ValidateCodeFilter extends OncePerRequestFilter {
    private LoginFailureHandler loginFailureHandler;

    public void setLoginFailureHandler(LoginFailureHandler loginFailureHandler) {
        this.loginFailureHandler = loginFailureHandler;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        //判断是否为登录请求
        if (httpServletRequest.getRequestURI().contains(Constant.LOGIN_URL) && Constant.POST_STRING.equalsIgnoreCase(httpServletRequest.getMethod())) {
            String code = httpServletRequest.getParameter("validateCode");
            String sessionCode = httpServletRequest.getSession().getAttribute("checkCode").toString();

            try {
                if (code == null) {
                    throw new AuthenticationServiceException("请输入验证码");
                }
                if (sessionCode==null || !code.trim().toUpperCase().equals(sessionCode.toUpperCase())) {
                    throw new AuthenticationServiceException("验证码不正确");
                }
            } catch (AuthenticationException e) {
                //交给自定义失败处理类处理
                loginFailureHandler.onAuthenticationFailure(httpServletRequest, httpServletResponse, e);
                return;
            }
            UserServiceImpl.USER_TYPE = Integer.valueOf(httpServletRequest.getParameter("userType"));

        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
