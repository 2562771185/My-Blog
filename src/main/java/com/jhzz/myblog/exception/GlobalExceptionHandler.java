package com.jhzz.myblog.exception;

import com.jhzz.myblog.common.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

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
    /**
     * 校验异常拦截器
     * @param exception
     * @return
     */
    @ExceptionHandler({MethodArgumentNotValidException.class, BindException.class})
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseResult handleMethodArgumentNotValidException(Exception exception) {
        StringBuilder errorInfo = new StringBuilder();
        BindingResult bindingResult=null;
        if(exception instanceof MethodArgumentNotValidException){
            bindingResult= ((MethodArgumentNotValidException)exception).getBindingResult();
        }
        if(exception instanceof BindException){
            bindingResult= ((BindException)exception).getBindingResult();
        }
        for(int i = 0; i < bindingResult.getFieldErrors().size(); i++){
            if(i > 0){
                errorInfo.append(",");
            }
            FieldError fieldError = bindingResult.getFieldErrors().get(i);
            errorInfo.append(fieldError.getField()).append(" :").append(fieldError.getDefaultMessage());
        }
        log.error(errorInfo.toString());
        //这里返回自己的Result的结果类。
        return ResponseResult.errorResult(500,errorInfo.toString());
    }

    @ExceptionHandler(BlogException.class)
    @ResponseBody
    public ResponseResult error(BlogException e){
        log.error(e.getMessage());
        return ResponseResult.errorResult(e.getCode(),e.getMsg());
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseResult error(Exception e){
        e.printStackTrace();
        return ResponseResult.errorResult(500,"出错了(#^.^#)请联系管理员!");
    }

}
