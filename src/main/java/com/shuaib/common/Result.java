package com.shuaib.common;

import lombok.Data;

import java.io.Serializable;

/**
 * @author shuai   b
 * @description 通用返回格式类
 * @createDate 2022-11-02 17:50:00
 */
@Data
public class Result implements Serializable {
    //状态码
    public int code;
    //返回信息
    public String msg;
    //返回的数据
    public Object data;

    public static Result success() {
        Result result = new Result();
        result.setCode(200);
        result.setMsg("操作成功");
        return result;
    }

    public static Result success(String msg) {
        Result result = new Result();
        result.setCode(200);
        result.setMsg(msg);
        return result;
    }

    public static Result success(int code,String msg) {
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }

    public static Result success(int code,String msg,Object data) {
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        result.setData(data);
        return result;
    }

    public static Result success(Object data) {
        Result result = new Result();
        result.setCode(200);
        result.setMsg("操作成功");
        result.setData(data);
        return result;
    }

    public static Result error() {
        Result result = new Result();
        result.setCode(500);
        result.setMsg("操作失败,未知的服务器错误");
        return result;
    }

    public static Result error(String msg) {
        Result result = new Result();
        result.setCode(500);
        result.setMsg(msg);
        return result;
    }

    public static Result error(int code,String msg) {
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }

    public static Result error(int code,String msg,Object data) {
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        result.setData(data);
        return result;
    }
}


