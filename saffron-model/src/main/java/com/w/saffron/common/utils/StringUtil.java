package com.w.saffron.common.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class StringUtil {

    public String fuzzyLeft(String str){
        return "%"+str;
    }

    public String fuzzyRight(String str){
        return str+"%";
    }

    public String fuzzy(String str){
        return "%"+str+"%";
    }


}
