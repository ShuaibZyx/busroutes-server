package com.shuaib.common;

import lombok.Data;

import java.io.Serializable;

@Data
public class Result implements Serializable {
    public int code;
    public String msg;
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


