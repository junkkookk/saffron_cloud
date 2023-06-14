package com.w.saffron.tool;

import cn.hutool.extra.spring.SpringUtil;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author w
 * @since 2023/3/6
 */
@Component
@ConfigurationProperties(prefix = "saffron", ignoreInvalidFields = true)
@Getter
@Setter
public class SaffronInfo {

    private Boolean enableJob;

    private Boolean enableListener;

    public static String getAppName(){
        return SpringUtil.getProperty("spring.application.name");
    }


}
