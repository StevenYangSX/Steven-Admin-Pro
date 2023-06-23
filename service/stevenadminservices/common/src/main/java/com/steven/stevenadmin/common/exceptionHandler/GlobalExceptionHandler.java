package com.steven.stevenadmin.common.exceptionHandler;

import com.steven.stevenadmin.common.utils.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;


@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public static ApiResponse<Object> error(Exception e) {
//        e.printStackTrace();
        return ApiResponse.error().message("服务器全局异常").data(e);
    }

    //特定异常处理
    @ExceptionHandler(ArithmeticException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public static ApiResponse<Object> arithmeticError(ArithmeticException e) {
        e.printStackTrace();
        return ApiResponse.error().message("服务器逻辑错误").data(e);
    }

    @ExceptionHandler(CustomExceptionHandler.class)
    @ResponseBody
    public static ApiResponse<Object> customError(CustomExceptionHandler e) {
        return ApiResponse.error().code(e.getCode()).message(e.getMessage());
    }
}


