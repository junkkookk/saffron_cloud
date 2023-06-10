package com.w.saffron.video.constant;

import com.w.saffron.common.enumrate.BaseEnum;
import lombok.AllArgsConstructor;

/**
 * @author w
 * @since 2023/5/20
 */
@AllArgsConstructor
public enum Category implements BaseEnum {

    JAPAN(1,"日本"),
    CHINA(2,"国产"),
    LIVE(3,"套路"),
    EUR_AND_AMER(4,"欧美");

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

    public static class Converter extends ConvertBase<Category>{
        public Converter() {
            super(Category.class);
        }
    }

}
