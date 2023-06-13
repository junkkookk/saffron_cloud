package com.w.saffron.config;

import cn.dev33.satoken.reactor.filter.SaReactorFilter;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.same.SaSameUtil;
import cn.dev33.satoken.stp.StpInterface;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.json.JSONUtil;
import com.w.saffron.common.R;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;

import java.util.Collections;
import java.util.List;

/**
 * @author w
 * @since 2023/6/13
 */
@Configuration
public class GateSecurityConfig {



    @Bean
    public SaReactorFilter getSaReactorFilter() {
        return new SaReactorFilter()
                // 拦截地址
                .addInclude("/**")    /* 拦截全部path */
                // 开放地址
                .addExclude("/favicon.ico")
                // 鉴权方法：每次访问进入
                .setAuth(obj -> {
                    // 登录校验 -- 拦截所有路由，并排除/user/doLogin 用于开放登录
                    SaRouter.match("/**", "/saffron-server/login", r -> StpUtil.checkLogin());
                })
                // 异常处理方法：每次setAuth函数出现异常时进入
                .setError(e -> JSONUtil.parseObj(R.error().msg(e.getMessage())));
    }

    @Bean
    public StpInterface stpInterface(){
        return new StpInterface() {
            @Override
            public List<String> getPermissionList(Object userId, String s) {

                return Collections.emptyList();
            }

            @Override
            public List<String> getRoleList(Object o, String s) {
                return Collections.emptyList();
            }
        };
    }

    @Bean
    public GlobalFilter sameTokenFilter(){
        return (exchange, chain) -> {
            ServerHttpRequest newRequest = exchange
                    .getRequest()
                    .mutate()
                    // 为请求追加 Same-Token 参数
                    .header(SaSameUtil.SAME_TOKEN, SaSameUtil.getToken())
                    .build();
            ServerWebExchange newExchange = exchange.mutate().request(newRequest).build();
            return chain.filter(newExchange);
        };
    }
}
