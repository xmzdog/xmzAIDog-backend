package com.onepiece.xmz.service.weixin;

import com.onepiece.xmz.domain.req.WeixinQrCodeReq;
import com.onepiece.xmz.domain.res.WeixinQrCodeRes;
import com.onepiece.xmz.domain.res.WeixinTokenRes;
import com.onepiece.xmz.domain.vo.WeixinTemplateMessageVO;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created with IntelliJ IDEA.
 * User: xiangmz
 * Date: 2025/4/20
 * Time: 21:33
 * Description: 微信API服务  retrofit2
 */
public interface IWeixinApiService {

    /**
     *
     * @param grantType     获取access_token 填写client_credential
     * @param appId         第三方用户唯一凭证
     * @param appSecret     第三方用户唯一凭证密钥，即appsecret
     * @return  响应结果
     */
    @GET("cgi-bin/token")
    Call<WeixinTokenRes> getToken(@Query("grant_type") String grantType,
                                  @Query("appid") String appId,
                                  @Query("secret") String appSecret);

    /**
     * 获取凭据 ticket
     * 文档：<a href="https://developers.weixin.qq.com/doc/offiaccount/Account_Management/Generating_a_Parametric_QR_Code.html">Generating_a_Parametric_QR_Code</a>
     * <a href="https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=TICKET">前端根据凭证展示二维码</a>
     *
     * @param accessToken            getToken 获取的 token 信息
     * @param weixinQrCodeReq 入参对象
     * @return 应答结果
     */
    @POST("cgi-bin/qrcode/create")
    Call<WeixinQrCodeRes> createQrCode(@Query("access_token") String accessToken, @Body WeixinQrCodeReq weixinQrCodeReq);

    /**
     * 发送微信公众号模板消息
     * 文档：https://mp.weixin.qq.com/debug/cgi-bin/readtmpl?t=tmplmsg/faq_tmpl
     *
     * @param accessToken              getToken 获取的 token 信息
     * @param weixinTemplateMessageVO 入参对象
     * @return 应答结果
     */
    @POST("cgi-bin/message/template/send")
    Call<Void> sendMessage(@Query("access_token") String accessToken, @Body WeixinTemplateMessageVO weixinTemplateMessageVO);


}
