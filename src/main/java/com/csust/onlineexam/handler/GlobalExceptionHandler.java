package com.csust.onlineexam.handler;

import com.csust.onlineexam.dto.Result;
import com.csust.onlineexam.dto.ResultCode;
import org.springframework.web.bind.annotation.ControllerAdvice;

/**
 * @author ：Lennovo
 * @date ：Created in 2020/3/26 16:43
 * @description： 用于全局统一异常处理器
 * @modified By：
 */
@ControllerAdvice
public class GlobalExceptionHandler {


    /**
     * 通用异常处理方法
     * @param e 错误类
     * @return 通用异常结果
     */
    public Result error(Exception e){

        e.printStackTrace();
        Result result = new Result();
        result.setResultCode(ResultCode.UNKNOWN_ERROR);
        result.setData(e.getMessage());
        return result;
    }

    /**
     * 空指针异常处理方法
     * @param e 空指针异常类
     * @return 错误结果提示
     */
    public Result error(NullPointerException e){
        e.printStackTrace();
        Result result = new Result();
        result.setResultCode(ResultCode.NULL_POINTER);
        result.setData(e.getMessage());
        return result;
    }

}
