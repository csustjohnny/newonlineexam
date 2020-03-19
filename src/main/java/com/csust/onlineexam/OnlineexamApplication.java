package com.csust.onlineexam;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Lenovo
 */
@SpringBootApplication
@MapperScan("com.csust.onlineexam.mapper")
public class OnlineexamApplication {

    public static void main(String[] args) {
        SpringApplication.run(OnlineexamApplication.class, args);
    }

}
