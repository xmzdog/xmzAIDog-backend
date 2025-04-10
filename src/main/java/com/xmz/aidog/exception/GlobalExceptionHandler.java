package com.xmz.aidog.exception;


import com.xmz.aidog.common.BaseResponse;
import com.xmz.aidog.model.enums.ErrorCode;
import com.xmz.aidog.utils.ResultUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


/**
 * Created with IntelliJ IDEA.
 * User: xiangmz
 * Date: 2025/4/10
 * Time: 22:15
 * Description: 全局异常处理器
 * 全局异常处理器（使用 @RestControllerAdvice + @ExceptionHandler）是 Spring Boot 提供的一种统一处理所有 Controller 层异常的机制。
 *
 * @RestControllerAdvice：标记一个类为 全局 Controller 异常处理器。
 * @ExceptionHandler：在这个类中，定义具体捕获哪种类型的异常，并返回什么内容。
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    // 处理自定义业务异常
    @ExceptionHandler(BusinessException.class)
    public BaseResponse<?> businessExceptionHandler(BusinessException e) {
        return ResultUtils.error(e.getCode(), e.getMessage());
    }

    // 处理其他未知异常
    @ExceptionHandler(Exception.class)
    public BaseResponse<?> systemExceptionHandler(Exception e) {
        // 打印异常日志（可选）
        e.printStackTrace();
        return ResultUtils.error(ErrorCode.SYSTEM_ERROR.getCode(), "系统错误，请联系管理员");
    }
}
