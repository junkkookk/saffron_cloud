package com.w.saffron.video.constant;

import com.w.saffron.common.enumrate.BaseEnum;
import lombok.AllArgsConstructor;

/**
 * @author w
 * @since 2023/5/20
 */
@AllArgsConstructor
public enum Status implements BaseEnum {

    DEFAULT(0,"未采集"),
    READY(1,"已采集");

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

    public static class Converter extends ConvertBase<Status>{
        public Converter() {
            super(Status.class);
        }
    }
}
