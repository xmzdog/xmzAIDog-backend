package com.onepiece.xmz.controller;

import com.onepiece.xmz.common.constants.Constants;
import com.onepiece.xmz.common.response.Response;
import com.onepiece.xmz.service.ILoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * Created with IntelliJ IDEA.
 * User: xiangmz
 * Date: 2025/4/20
 * Time: 23:15
 * Description: 登陆服务
 */
@Slf4j
@RestController()
@CrossOrigin("*")
@Api(tags = "扫码登陆接口")
@RequestMapping("/api/v1/login/")
public class LoginController {

    @Resource
    private ILoginService loginService;

    /**
     *
     * @return
     */
    @ApiOperation(value = "获取公众号登陆凭证ticket")
    @RequestMapping(value = "weixin_qrcode_ticket", method = RequestMethod.GET)
    public Response<String> weixinQrCodeTicket() {
        try {
            String qrCodeTicket = loginService.createQrCodeTicket();
            log.info("生成微信扫码登录 ticket:{}", qrCodeTicket);
            return Response.<String>builder()
                    .code(Constants.ResponseCode.SUCCESS.getCode())
                    .info(Constants.ResponseCode.SUCCESS.getInfo())
                    .data(qrCodeTicket)
                    .build();
        } catch (Exception e) {
            log.error("生成微信扫码登录 ticket 失败", e);
            return Response.<String>builder()
                    .code(Constants.ResponseCode.UN_ERROR.getCode())
                    .info(Constants.ResponseCode.UN_ERROR.getInfo())
                    .build();
        }
    }

    /**
     *
     */
    @ApiOperation(value = "检查登陆结果", notes = "根据微信扫码返回的 ticket，检测登录状态并获取对应的 openidToken")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ticket", value = "微信扫码返回的 ticket，用于验证用户登录状态", required = true, dataType = "String", paramType = "query")
    })
    @RequestMapping(value = "check_login", method = RequestMethod.GET)
    public Response<String> checkLogin(@RequestParam String ticket) {
        try {
            String openidToken = loginService.checkLogin(ticket);
            log.info("扫码检测登录结果 ticket:{} openidToken:{}", ticket, openidToken);
            if (StringUtils.isNotBlank(openidToken)) {
                return Response.<String>builder()
                        .code(Constants.ResponseCode.SUCCESS.getCode())
                        .info(Constants.ResponseCode.SUCCESS.getInfo())
                        .data(openidToken)
                        .build();
            } else {
                return Response.<String>builder()
                        .code(Constants.ResponseCode.NO_LOGIN.getCode())
                        .info(Constants.ResponseCode.NO_LOGIN.getInfo())
                        .build();
            }
        } catch (Exception e) {
            log.error("扫码检测登录结果失败 ticket:{}", ticket, e);
            return Response.<String>builder()
                    .code(Constants.ResponseCode.UN_ERROR.getCode())
                    .info(Constants.ResponseCode.UN_ERROR.getInfo())
                    .build();
        }
    }

}
