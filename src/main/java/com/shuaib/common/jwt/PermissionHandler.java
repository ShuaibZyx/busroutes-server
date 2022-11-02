package com.shuaib.common.jwt;

import com.shuaib.common.Result;
import io.jsonwebtoken.SignatureException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class PermissionHandler {
    @ExceptionHandler(value = { SignatureException.class })
    @ResponseBody
    public Result authorizationException(SignatureException e){
        return Result.error(401, "无权访问," + e.getMessage());
    }
}