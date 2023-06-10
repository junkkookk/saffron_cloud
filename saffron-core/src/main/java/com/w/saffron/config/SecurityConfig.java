package com.w.saffron.config;

import cn.dev33.satoken.config.SaTokenConfig;
import cn.dev33.satoken.context.SaHolder;
import cn.dev33.satoken.filter.SaServletFilter;
import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.json.JSONUtil;
import com.w.saffron.common.R;
import com.w.saffron.common.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author w
 * @since 2023/3/21
 */
@Configuration
@Slf4j
public class SecurityConfig implements WebMvcConfigurer {

    @Bean
    public SaServletFilter saServletFilter(){
        return new SaServletFilter()
                .addExclude("/**")
                .addExclude("/file/**")
                .addExclude("/oauth/**")
                .addExclude("/config/get")
                .addExclude("/front/**")
                .addExclude("/mail/get-captcha")
                .setAuth(auth->{
                    SaRouter.match("/**","/login", StpUtil::checkLogin);

                })
                .setError(e->{
                    SaHolder.getResponse().setHeader("Content-Type", "application/json;charset=UTF-8");
                    return JSONUtil.toJsonStr(R.error(ResultCode.NOT_AUTHORIZE).msg(e.getMessage()));
                });
    }

    @Bean
    @Primary
    public SaTokenConfig saTokenConfig(){
        SaTokenConfig config = new SaTokenConfig();
        config.setTokenName("Authorization");
        config.setTimeout(7*24 * 60 * 60);
        config.setActivityTimeout(-1);
        config.setIsConcurrent(false);
        config.setIsShare(false);
        config.setIsLog(false);
        config.setIsPrint(false);
        config.setIsReadCookie(false);
        config.setTokenStyle("uuid");
        config.setAutoRenew(false);
        config.setTokenPrefix("Bearer");
        return config;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new SaInterceptor()).addPathPatterns("/**");
    }
}
