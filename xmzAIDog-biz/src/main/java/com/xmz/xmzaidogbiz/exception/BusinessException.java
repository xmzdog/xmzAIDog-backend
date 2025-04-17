package com.xmz.xmzaidogbiz.exception;

import com.xmz.xmzaidogbiz.model.enums.ErrorCode;
import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 * User: xiangmz
 * Date: 2025/4/10
 * Time: 22:15
 * Description: 自定义异常类
 * 业务逻辑错误的统一处理。
 * 易于维护和扩展，减少硬编码，遵循统一的错误处理规范。
 * 增强语义性，让异常具有清晰的业务上下文。
 * 集中管理错误码和信息，提高代码一致性和可读性。
 */
@Data
public class BusinessException extends RuntimeException {

    private final int code;

    /**
     *
     * @param errorCode
     *  super 是用来调用父类的构造方法，必须在第一行
     */
    public BusinessException(ErrorCode errorCode){
        super(errorCode.getMessage());  // 这里将自定义的错误信息message 传给 RuntimeException
        this.code = errorCode.getCode();    // 这里保存自定义的错误码，返回给前端
    }

    /**
     *
     * @param errorCode
     * @param message 自定义返回错误信息
     */
    public BusinessException(ErrorCode errorCode,String message){
        super(message);
        this.code = errorCode.getCode();
    }
}
