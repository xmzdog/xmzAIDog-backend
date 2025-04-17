package com.xmz.xmzaidogbiz.common;

import com.xmz.xmzaidogbiz.model.enums.ErrorCode;
import lombok.Data;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: xiangmz
 * Date: 2025/4/10
 * Time: 15:00
 * Description: 通用的返回类
 */
@Data
public class BaseResponse<T> implements Serializable {
    private int code;
    private T data;
    private String message;

    public BaseResponse(int code, T data, String message) {
        this.code = code;
        this.data = data;
        this.message = message;
    }

    public BaseResponse(ErrorCode errorCode){
        this.code = errorCode.getCode();
        this.data = null;
        this.message = errorCode.getMessage();
    }

    public BaseResponse(int code,String message){
        this.code = code;
        this.data = null;
        this.message = message;
    }
}
