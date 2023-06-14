package com.w.saffron.schdule;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author w
 * @since 2023/4/21
 */
@ConfigurationProperties(prefix = "spring.xxl")
@Component
@Getter
@Setter
public class XxlJobProperties {

    private String url;

    private String accessToken;

    private String appName;

    private String port;

    private Integer logRetentionDays;

}
