package com.w.saffron.utils;

import lombok.experimental.UtilityClass;

import java.util.Date;

/**
 * @author w
 * @since 2023/3/29
 */
@UtilityClass
public class DateUtil {

    public static final String YYYY_MM_DD="yyyyMMdd";
    public static final String YYYY_MM_DD_HH_MM_SS="yyyy-MM-dd HH:mm:ss";
    public String getCurrentDate() {
        return cn.hutool.core.date.DateUtil.format(new Date(),YYYY_MM_DD);
    }
}
