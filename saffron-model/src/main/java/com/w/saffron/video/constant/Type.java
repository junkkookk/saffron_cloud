package com.w.saffron.video.constant;

import com.w.saffron.common.enumrate.BaseEnum;
import lombok.AllArgsConstructor;

/**
 * @author w
 * @since 2023/5/20
 */
@AllArgsConstructor
public enum Type implements BaseEnum {

    M3U8(1,"m3u8"),
    MP4(2,"mp4");

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


    public static class Converter extends ConvertBase<Type>{
        public Converter() {
            super(Type.class);
        }
    }

}
