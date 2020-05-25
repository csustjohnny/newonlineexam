package com.csust.onlineexam.config;

import com.csust.onlineexam.filter.ValidateCodeFilter;
import com.csust.onlineexam.handler.LoginFailureHandler;
import com.csust.onlineexam.handler.LoginSuccessHandler;
import com.csust.onlineexam.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author ：Lenovo.
 * @date ：Created in 14:27 2020/3/18
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserServiceImpl userService;
    @Autowired
    public SecurityConfig(UserServiceImpl userService) {
        this.userService = userService;
    }
    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        //swagger api json
        web.ignoring().antMatchers("/v2/api-docs",
                //用来获取支持的动作
                "/swagger-resources/configuration/ui",
                //用来获取api-docs的URI
                "/swagger-resources",
                //安全选项
                "/swagger-resources/configuration/security",
                "/swagger-ui.html",
                "/webjars/**","/css/**","/images/**","/js/**","/lib/**","/login/checkCode");

    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        ValidateCodeFilter validateCodeFilter = new ValidateCodeFilter();
        validateCodeFilter.setLoginFailureHandler(new LoginFailureHandler());
        http.addFilterBefore(validateCodeFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/student/**").hasRole("STUDENT")
                .antMatchers("/teacher/**").hasRole("TEACHER")
                .antMatchers("/question/**").hasAnyRole("ADMIN","TEACHER")
                .antMatchers("/subject/**").hasAnyRole("ADMIN","TEACHER","STUDENT")
                .antMatchers("/exam/**").hasAnyRole("ADMIN","TEACHER")
                .antMatchers("/course/**","/test-paper/**").hasAnyRole("ADMIN","TEACHER")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/login/check").permitAll()
                .successHandler(new LoginSuccessHandler())
                .failureHandler(new LoginFailureHandler())
                .and()
                .headers().frameOptions().disable()
                .and()
                .csrf().disable();
    }
}
