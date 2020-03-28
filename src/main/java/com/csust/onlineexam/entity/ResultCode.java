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
    SUCCESS(200,"操作成功"),

    /*数据错误20000-29999*/
    UPDATE_FAILURE(200001,"数据错误"),
    INSERT_FAILURE(200002,"新增失败，请检查数据是否正确"),
    INSERT_DUPLICATE(200003,"新增失败，已有此用户！"),
    PARAMS_INCORRECT(200004,"参数不正确"),
    DELETE_FAILURE(20005,"删除操作失败,请检查是否存在后重试"),
    /*系统错误*/
    NULL_POINTER(30001,"空指针异常"),
    /*其他错误50000-59999*/
    UNKNOWN_ERROR(50000,"未知错误");

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
