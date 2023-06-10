package com.w.saffron.common.utils;

import lombok.experimental.UtilityClass;
import org.springframework.util.NumberUtils;

/**
 * @author w
 * @since 2023/3/14
 */
@UtilityClass
public class NumberUtil {

    public Integer toInt(String str){
        return NumberUtils.parseNumber(str,Integer.class);
    }


}
