package com.shuaib.common;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;

@ControllerAdvice
public class MyExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Result exceptionHandler(Exception e) {
        System.out.println("异常原因:" + Arrays.toString(Arrays.copyOfRange(e.getStackTrace(), 0, 5)).replace(",", "\n"));
        return Result.error(e.getMessage());
    }
}
