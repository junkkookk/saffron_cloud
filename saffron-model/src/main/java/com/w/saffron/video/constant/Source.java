package com.w.saffron.video.constant;

import com.w.saffron.common.enumrate.BaseEnum;
import lombok.AllArgsConstructor;

/**
 * @author w
 * @since 2023/5/20
 */

@AllArgsConstructor
public enum Source implements BaseEnum {

    ZMQ(1,"爱美足"),
    FOOT52(2,"吾爱美足");

    private final Integer code;
    private final String desc;

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getDesc() {
        return desc;
    }

    public static class Converter extends ConvertBase<Source>{
        public Converter() {
            super(Source.class);
        }
    }
}
