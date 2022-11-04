package com.shuaib.common;

import org.jetbrains.annotations.NotNull;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@RestControllerAdvice
public class MyExceptionHandler {

    /**
     * 全局运行异常处理
     * @param e 异常对象
     * @return 通用返回格式
     */
    @ExceptionHandler(value = RuntimeException.class)
    public Result exceptionHandler(@NotNull Exception e) {
        System.out.println("异常原因:" + Arrays.toString(Arrays.copyOfRange(e.getStackTrace(), 0, 5)).replace(",", "\n"));
        return Result.error(e.getMessage());
    }

    /**
     * 全局方法参数校验异常处理
     * @param e 异常对象
     * @return 通用返回格式
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public Result validateException(@NotNull MethodArgumentNotValidException e) {
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        String s = fieldErrors.get(0).getField() + fieldErrors.get(0).getDefaultMessage();
        return Result.error("参数有误！" + s);
    }


    @ExceptionHandler(value = ConstraintViolationException.class)
    public Result validateException(@NotNull ConstraintViolationException e) {
        Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
        List<String> list = new ArrayList<>();
        for (ConstraintViolation<?> item : violations) {
            list.add(item.getMessage());
        }
        return Result.error("参数有误！" + list.get(0));
    }
}
