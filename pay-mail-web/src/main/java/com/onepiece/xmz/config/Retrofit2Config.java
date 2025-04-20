package com.onepiece.xmz.config;

import com.onepiece.xmz.service.weixin.IWeixinApiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * Created with IntelliJ IDEA.
 * User: xiangmz
 * Date: 2025/4/20
 * Time: 22:36
 * Description: 使用 Retrofit + Jackson 作为 HTTP 客户端，配置了微信 API 的基础请求入口，并将接口服务 IWeixinApiService 注册为 Spring Bean 供项目中注入使用。
 * Retrofit 客户端初始化入口，负责配置请求基础地址、注册 JSON 转换器，并将接口服务代理注册到 Spring 容器中，极大简化了后续 HTTP 请求的调用方式。
 */
@Slf4j
@Configuration
public class Retrofit2Config {

    private static final String BASE_URL = "https://api.weixin.qq.com/";

    @Bean
    public Retrofit retrofit() {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(JacksonConverterFactory.create()).build(); //Jackson 转换器，用于把 HTTP 接口返回的 JSON 转换为 Java 对象（也支持请求体序列化为 JSON）
    }

    /**
     * 接口代理注入
     * @param retrofit
     * @return
     * 使用 retrofit.create(...) 创建 IWeixinApiService 的实现类
     * 并注册为 Spring 的 Bean，后续你只需要 @Autowired / @Resource 注入即可调用微信接口
     */
    @Bean
    public IWeixinApiService weixinApiService(Retrofit retrofit) {
        return retrofit.create(IWeixinApiService.class);
    }

}

