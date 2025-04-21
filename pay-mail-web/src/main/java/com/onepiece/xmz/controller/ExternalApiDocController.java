package com.onepiece.xmz.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/external")
@Api(tags = "第三方接口")
public class ExternalApiDocController {

    @ApiOperation(value = "微信扫码登录二维码接口（外部接口）",
            notes = "请使用以下链接访问微信二维码接口，替换 `ticket` 参数：\n" +
                    "https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket={ticket}")
    @GetMapping("/weixin-qrcode")
    public String weixinQrcodeDoc(@RequestParam String ticket) {
        // 拼接 URL
        String qrCodeUrl = "https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=" + ticket;
        return "请点击链接查看二维码： " + qrCodeUrl;
    }
}


