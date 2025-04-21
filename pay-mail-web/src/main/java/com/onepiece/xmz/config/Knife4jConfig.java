package com.onepiece.xmz.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Created with IntelliJ IDEA.
 * User: xiangmz
 * Date: 2025/4/10
 * Time: 13:21
 * Description: Knife4jConfig 是用于配置 Knife4j 的类，Knife4j 是一个基于 Swagger UI 的增强型 API 文档生成工具，通常用于在 Spring Boot 项目中生成和展示 API 文档。
 *
 * 访问地址：http://localhost:8101/xmz/doc.html
 */
@Configuration
@EnableSwagger2
@Profile({"dev", "test"})
public class Knife4jConfig {

    @Bean
    public Docket defaultApi2() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(new ApiInfoBuilder()
                        .title("ONEPIECE-接口文档")
                        .description("xmzAIDog")
                        .version("1.0")
                        .build())
                .select()
                // 指定 Controller 扫描包路径
                .apis(RequestHandlerSelectors.basePackage("com.onepiece.xmz.controller"))
                .paths(PathSelectors.any())
                .build();
    }
}