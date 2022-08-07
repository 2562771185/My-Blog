package com.jhzz.myblog.exception;

import com.jhzz.myblog.common.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * \* Created with IntelliJ IDEA.
 * \* @author: Huanzhi
 * \* Date: 2022/5/18
 * \* Time: 23:45
 * \* Description:统一异常处理类
 * \
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseResult error(Exception e){
        e.printStackTrace();
        return ResponseResult.errorResult(500,"出错了(#^.^#)请联系管理员!");
    }
    @ExceptionHandler(BlogException.class)
    @ResponseBody
    public ResponseResult error(BlogException e){
        log.error(e.getMessage());
        return ResponseResult.errorResult(e.getCode(),e.getMsg());
    }

}
