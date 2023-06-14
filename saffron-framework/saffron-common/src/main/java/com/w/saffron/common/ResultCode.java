package com.w.saffron.common;

import lombok.Getter;

import java.util.Arrays;
@Getter
public enum ResultCode {

    SUCCESS(200,"success"),
    BAD_REQUEST(400,"bad request"),
    NOT_AUTHORIZE(403,"unauthorized"),
    NOT_FOUND(404,"not found"),
    SYSTEM_ERROR(500,"internal system error");

    final Integer code;
    final String msg;

    ResultCode(Integer code, String msg){
        this.code = code;
        this.msg = msg;
    }

    public static ResultCode of(Integer code){
       return Arrays.stream(ResultCode.values())
                .filter(resultCode -> resultCode.getCode().equals(code))
                .findFirst()
               .orElse(SYSTEM_ERROR);
    }
}
