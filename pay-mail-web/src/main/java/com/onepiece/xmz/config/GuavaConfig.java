package com.onepiece.xmz.config;

//import com.google.common.cache.Cache;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA.
 * User: xiangmz
 * Date: 2025/4/20
 * Time: 22:33
 * Description: 基于 Guava Cache 创建两个本地缓存组件，分别用于缓存 weixinAccessToken 和 openidToken，并将它们注册为 Spring 的 Bean，方便在项目中直接注入使用。
 *
 * 配置类，spring项目启动时会扫描并加载
 */
@Configuration
public class GuavaConfig {

    @Bean(name = "weixinAccessToken")   // 手动向 Spring 容器注册两个名为 weixinAccessToken 和 openidToken 的缓存实例。
    public Cache<String, String> weixinAccessToken() {
        return CacheBuilder.newBuilder()
                .expireAfterWrite(2, TimeUnit.HOURS)
                .build();
    }

    @Bean(name = "openidToken")
    public Cache<String, String> openidToken() {
        return CacheBuilder.newBuilder()
                .expireAfterWrite(1, TimeUnit.HOURS)
                .build();
    }

}
