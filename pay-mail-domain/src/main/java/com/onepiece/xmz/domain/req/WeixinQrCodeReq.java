package com.onepiece.xmz.domain.req;

import lombok.*;

/**
 * Created with IntelliJ IDEA.
 * User: xiangmz
 * Date: 2025/4/20
 * Time: 22:19
 * Description: 获取微信登录二维码请求对象
 */
@Data
@Builder    //  Lombok 提供的一个非常强大的注解，用于为一个类自动生成 Builder 模式 的构造器代码，让你可以用链式方式来创建对象，既优雅又不易出错。
@AllArgsConstructor
@NoArgsConstructor
public class WeixinQrCodeReq {

    private int expire_seconds;
    private String action_name;
    private ActionInfo action_info;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ActionInfo {
        Scene scene;

        @Data
        @Builder
        @AllArgsConstructor
        @NoArgsConstructor
        public static class Scene {
            int scene_id;
            String scene_str;
        }
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public enum ActionNameTypeVO {
        QR_SCENE("QR_SCENE", "临时的整型参数值"),
        QR_STR_SCENE("QR_STR_SCENE", "临时的字符串参数值"),
        QR_LIMIT_SCENE("QR_LIMIT_SCENE", "永久的整型参数值"),
        QR_LIMIT_STR_SCENE("QR_LIMIT_STR_SCENE", "永久的字符串参数值");

        private String code;
        private String info;
    }

}

