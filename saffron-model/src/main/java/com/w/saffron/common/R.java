package com.w.saffron.common;



import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
public class R<T> {

    private Integer code;

    private String msg;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    public R<T> resultCode(ResultCode resultCode){
        this.code = resultCode.getCode();
        this.msg = resultCode.getMsg();
        return this;
    }

    public static <T> R<T> ok() {
        R<T> r = new R<>();
        return r.resultCode(ResultCode.SUCCESS);
    }

    public static <T> R<T> ok(T data) {
        R<T> r = new R<>();
        r.data = data;
        return r.resultCode(ResultCode.SUCCESS);
    }

    public static <T> R<T> error() {
        R<T> r = new R<>();
        return r.resultCode(ResultCode.SYSTEM_ERROR);
    }

    public static <T> R<T> error(ResultCode resultCode) {
        R<T> r = new R<>();
        return r.resultCode(resultCode);
    }

    public R<T> msg(String msg){
        this.msg = msg;
        return this;
    }

    public R<T> code(Integer code){
        this.code = code;
        return this;
    }

    public R<T> data(T data){
        this.data = data;
        return this;
    }

}
