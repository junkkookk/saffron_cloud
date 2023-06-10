package com.w.saffron.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author w
 * @since 2023/3/29
 */
@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer){
        configurer.setUseTrailingSlashMatch(true);
        configurer.setUseSuffixPatternMatch(true);
    }


}
