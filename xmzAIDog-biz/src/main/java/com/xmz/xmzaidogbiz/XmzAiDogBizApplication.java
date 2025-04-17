package com.xmz.xmzaidogbiz;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.xmz.xmzaidogbiz.mapper")
public class XmzAiDogBizApplication {

    public static void main(String[] args) {
        SpringApplication.run(XmzAiDogBizApplication.class, args);
    }

}
