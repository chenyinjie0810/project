package com.ideal.project.advice;

import com.ideal.project.common.dto.ResponseModel;
import com.ideal.project.common.enumerate.ResponseCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @Author: jcwang
 * @Description: 配置系统全局异常
 * @Date: 14:59 2019/10/25
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseModel handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        log.error("不支持当前请求方法", e);
        return ResponseModel.error(ResponseCode.SYS_ERROR, e.getMessage());
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseModel handleCDataIntegrityViolationException(DataIntegrityViolationException e) {
        log.error("记录存在唯一性冲突", e);
        return ResponseModel.error(ResponseCode.SYS_ERROR, "存在重复的记录");
    }

    /**
     * 参数绑定异常
     *
     * @param e
     * @return
     */
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = BindException.class)
    public ResponseModel handle(BindException e) {
        e.printStackTrace();
        BindingResult bindingResult = e.getBindingResult();
        FieldError error = bindingResult.getFieldError();
        String field = error.getField();
        String code = error.getDefaultMessage();
        String message = String.format("%s:%s", field, code);
        return ResponseModel.error(ResponseCode.INVALID_PARAMETER, message);
    }

    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = IllegalArgumentException.class)
    public ResponseModel handle(IllegalArgumentException e) {
        e.printStackTrace();
        return ResponseModel.error(ResponseCode.SYS_ERROR, e.getMessage());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Throwable.class)
    public ResponseModel handleException(Throwable e) {
        log.error("系统内部错误", e);
        StringWriter sw = new StringWriter();
        e.printStackTrace(new PrintWriter(sw, true));
        return ResponseModel.error(ResponseCode.SYS_ERROR, sw.getBuffer().toString());

    }
}
