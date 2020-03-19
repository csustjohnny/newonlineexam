package com.csust.onlineexam.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 用于swagger配置
 * @author ：Lenovo.
 * @date ：Created in 16:33 2020/3/16
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .pathMapping("/")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.csust.onlineexam.controller"))
                .paths(PathSelectors.any())
                .build().apiInfo(new ApiInfoBuilder()
                        .title("在线考试系统接口文档")
                        .description("详细信息....")
                        .version("1.0")
                        .contact(new Contact("网址", "http://www.shanyunbiao.cn", "962893304@qq.com"))
                        .license("蜀ICP备20004289")
                        .licenseUrl("http://www.beian.miit.gov.cn")
                        .build());
    }
}
