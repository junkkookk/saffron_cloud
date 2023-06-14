package com.w.saffron.config;

import cn.hutool.extra.spring.SpringUtil;
import com.w.saffron.utils.NumberUtil;
import com.w.saffron.schdule.XxlJobProperties;
import com.xxl.job.core.executor.impl.XxlJobSpringExecutor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author w
 * @since 2023/4/21
 */
@Configuration
@ConditionalOnProperty(prefix = "saffron",name = "enableJob",havingValue = "true")
@Slf4j
public class XxlConfig {
    @Bean
    public XxlJobSpringExecutor xxlJobSpringExecutor( @Autowired XxlJobProperties xxlJobProperties){
        XxlJobSpringExecutor jobSpringExecutor = new XxlJobSpringExecutor();
        jobSpringExecutor.setAdminAddresses(xxlJobProperties.getUrl());
        jobSpringExecutor.setAppname(xxlJobProperties.getAppName());
        jobSpringExecutor.setAccessToken(xxlJobProperties.getAccessToken());
        jobSpringExecutor.setPort(NumberUtil.toInt(xxlJobProperties.getPort()));
        jobSpringExecutor.setLogRetentionDays(xxlJobProperties.getLogRetentionDays());
        log.info("Xxl_admin url is {}",xxlJobProperties.getUrl());
        return jobSpringExecutor;
    }


}
