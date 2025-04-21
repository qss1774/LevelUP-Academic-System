package com.itheima.exception;

/**
 * ClassName:GlobalExceptionHandler
 * Package:com.itheima.exception
 * Description:
 *
 * @date:2025-04-09 12:59 a.m.
 * @author:Qss
 */

import com.itheima.pojo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler
    public Result handleException(Exception e) {
        log.error("程序出现异常：{}", e);
        return Result.error("出错了，请联系管理员");
    }

//    用于处理数据库中的主键冲突异常（DuplicateKeyException），
//    即当尝试插入的记录违反了数据库的唯一约束（如用户名已存在）时会触发该异常。
    @ExceptionHandler
    public Result handleDuplicateKeyException(DuplicateKeyException e) {
        log.error("程序出现异常：{}", e);
        String message = e.getMessage();
        int i = message.indexOf("Duplicate entry");
        String errMsg = message.substring(i);
        String[] arr = errMsg.split(" ");
        return Result.error(arr[2] + "已存在");
    }
}
