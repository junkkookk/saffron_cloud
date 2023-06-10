package com.w.saffron.common;

import cn.hutool.core.io.FileUtil;
import cn.hutool.extra.spring.SpringUtil;
import lombok.extern.slf4j.Slf4j;

import java.net.URL;

/**
 * @author w
 * @since 2023/3/6
 */
@Slf4j
public class SaffronInfo {

    private final static String TEMP_PATH;



    public final static String APP_NAME;

    static {
        URL url = Thread.currentThread().getContextClassLoader().getResource("");
        String path = null;
        if (url != null) {
            path = url.getPath();
        }
        TEMP_PATH = path+ "temp/";
        APP_NAME = SpringUtil.getProperty("spring.application.name");
    }

    public static String getTempPath() {
        return TEMP_PATH;
    }

    public static  void cleanTemp(){
        FileUtil.clean(getTempPath());
    }

    public static String getAesKey(){
        return SpringUtil.getProperty("spring.saffron.system.aes-key");
    }
}
