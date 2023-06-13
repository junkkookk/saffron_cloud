package com.w.saffron.exception;



import cn.hutool.core.util.StrUtil;
import com.w.saffron.common.R;
import com.w.saffron.common.exception.OprException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;


@RestControllerAdvice
@Slf4j
public class CustomExceptionHandler {

    @ExceptionHandler(OprException.class)
    public R<?> oprExceptionHandle(OprException e){
        return R.error()
                .code(e.getCode())
                .msg(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public R<?> customExceptionHandle(Exception e){
        e.printStackTrace();
        while (e.getCause()!=null){
            e = (Exception) e.getCause();
        }
        return R.error()
                .code(500)
                .msg(e.getMessage());
    }

    @ExceptionHandler(BindException.class)
    public R<?> bindException(BindException e){
        List<FieldError> errors = e.getBindingResult().getFieldErrors();
        List<String> msgList = errors.stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .filter(StrUtil::isNotBlank)
                .sorted()
                .collect(Collectors.toList());
        return R.error()
                .msg("请求参数错误")
                .code(405)
                .data(msgList);
    }

}
