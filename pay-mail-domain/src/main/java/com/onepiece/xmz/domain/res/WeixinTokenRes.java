package com.onepiece.xmz.domain.res;

import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 * User: xiangmz
 * Date: 2025/4/20
 * Time: 21:37
 * Description: 获取 Access token DTO 对象
 * access_token是公众号的全局唯一接口调用凭据，公众号调用各接口时都需使用access_token。开发者需要进行妥善保存。
 * access_token的存储至少要保留512个字符空间。access_token的有效期目前为2个小时，需定时刷新，重复获取将导致上次获取的access_token失效。
 * https://developers.weixin.qq.com/doc/offiaccount/Basic_Information/Get_access_token.html
 */
@Data
public class WeixinTokenRes {

    private String access_token;

    private String expires_in;

    private String errcode;
    private String errmsg;
}
