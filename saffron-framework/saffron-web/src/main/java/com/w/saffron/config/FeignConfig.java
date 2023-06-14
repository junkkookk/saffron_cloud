package com.w.saffron.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

/**
 * @author w
 * @since 2023/6/13
 */
@Configuration
@EnableFeignClients(basePackages = "com.w.saffron.rpc")
public class FeignConfig {

}
