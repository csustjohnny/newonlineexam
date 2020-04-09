package com.csust.onlineexam;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootTest
class OnlineexamApplicationTests {

    @Test
    void contextLoads() {
        System.out.println(new BCryptPasswordEncoder().encode("123456"));
    }

    @Test
    public void testEndWith(){
        //System.out.println("test.xls".endsWith("xlsx"));
    }
}
