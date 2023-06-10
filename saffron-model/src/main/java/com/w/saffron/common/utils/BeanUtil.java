package com.w.saffron.common.utils;

import cn.hutool.core.bean.copier.CopyOptions;
import lombok.experimental.UtilityClass;

/**
 * @author w
 * @since 2023/3/6
 */
@UtilityClass
public class BeanUtil {

    public static void copy(Object source,Object target){
        cn.hutool.core.bean.BeanUtil.copyProperties(source,target, CopyOptions.create().ignoreNullValue());
    }


}
