package com.onepiece.xmz;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created with IntelliJ IDEA.
 * User: xiangmz
 * Date: 2025/4/20
 * Time: 18:54
 * Description: 启动类   启动入口
 */
//todo: 整理SpringBoot的启动流程;  SpringBoot 如何实现自动配置的！！！！！！
@SpringBootApplication
@Configurable
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
