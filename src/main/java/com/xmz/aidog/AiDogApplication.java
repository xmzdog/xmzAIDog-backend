package com.xmz.aidog;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@MapperScan("com.xmz.aidog.mapper")
public class AiDogApplication {

    public static void main(String[] args) {
        SpringApplication.run(AiDogApplication.class, args);
    }

}
