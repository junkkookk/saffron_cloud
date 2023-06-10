package com.w.saffron.common.exception;


import com.w.saffron.common.ResultCode;

public class OprException extends RuntimeException{

    private final Integer code;

    public OprException(ResultCode resultCode){
        super(resultCode.getMsg());
        this.code = resultCode.getCode();
    }

    public OprException(Integer code, String msg){
        super(msg);
        this.code = code;
    }
    public OprException(String msg){
        super(msg);
        this.code = ResultCode.SYSTEM_ERROR.getCode();
    }

    public Integer getCode() {
        return code;
    }
}
