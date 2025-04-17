package com.xmz.xmzaidogbiz.utils;


import com.xmz.xmzaidogbiz.common.BaseResponse;
import com.xmz.xmzaidogbiz.model.enums.ErrorCode;

/**
 * Created with IntelliJ IDEA.
 * User: xiangmz
 * Date: 2025/4/10
 * Time: 14:04
 * Description: 返回工具类
 */
public class ResultUtils {

    public static <T> BaseResponse<T> sucess(T data){
        return new BaseResponse<>(0,data,"ok");
    }

    public static BaseResponse error(ErrorCode errorCode){
        return new BaseResponse(errorCode);
    }

    public static BaseResponse error(int code,String message){
        return new BaseResponse(code,message);
    }
}
