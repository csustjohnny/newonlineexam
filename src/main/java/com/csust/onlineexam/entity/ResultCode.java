package com.csust.onlineexam.entity;



/**
 * @author ：lennovo
 * @date ：Created on 2020/3/19 10:17
 * @description： API 统一返回状态码
 * @modified By：
 */
public enum ResultCode {
    /**
     *  成功状态码
     */
    SUCCESS(200,"success"),

    /*数据错误20000-29999*/
    UPDATE_FAILURE(200001,"数据错误");

    private Integer code;
    private String message;

    ResultCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
    public Integer getCode(){
        return code;
    }
    public String getMessage(){
        return message;
    }
}
