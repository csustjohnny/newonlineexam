package com.csust.onlineexam.handler;

import com.csust.onlineexam.dto.Result;
import com.csust.onlineexam.dto.ResultCode;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

/**
 * @author ：Lennovo
 * @date ：Created in 2020/3/26 16:43
 * @description： 用于全局统一异常处理器
 * @modified By：
 */
@RestControllerAdvice
public class GlobalExceptionHandler {


    /**
     * 通用异常处理方法
     * @param e 错误类
     * @return 通用异常结果
     */
    @ExceptionHandler(Exception.class)
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
    @ExceptionHandler(NullPointerException.class)
    public Result error(NullPointerException e){
        System.out.println("打印错误");
        e.printStackTrace();
        Result result = new Result();
        result.setResultCode(ResultCode.NULL_POINTER);
        result.setData(e.getMessage());
        return result;
    }
    @ExceptionHandler(SQLException.class)
    public Result error(SQLException e){
        e.printStackTrace();
        Result result = new Result();
        result.setResultCode(ResultCode.PARAMS_INCORRECT);
        result.setData(e.getMessage());
        return result;
    }
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public Result error(SQLIntegrityConstraintViolationException e){
        Result result = new Result();
        result.setResultCode(ResultCode.INSERT_DUPLICATE);
        result.setData(e.getMessage());
        return result;
    }

}
