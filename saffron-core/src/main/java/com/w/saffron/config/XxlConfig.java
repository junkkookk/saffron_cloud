package com.w.saffron.config;

import com.w.saffron.schdule.JobContext;
import com.w.saffron.schdule.XxlJobProperties;
import com.xxl.job.core.executor.impl.XxlJobSpringExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author w
 * @since 2023/4/21
 */
@Configuration
public class XxlConfig {


    @Bean
    public XxlJobSpringExecutor xxlJobSpringExecutor( @Autowired XxlJobProperties xxlJobProperties){
        XxlJobSpringExecutor jobSpringExecutor = new XxlJobSpringExecutor();
        jobSpringExecutor.setAdminAddresses(xxlJobProperties.getUrl());
        jobSpringExecutor.setAppname(xxlJobProperties.getAppName());
        jobSpringExecutor.setAccessToken(xxlJobProperties.getAccessToken());
        jobSpringExecutor.setPort(9999);
        jobSpringExecutor.setLogRetentionDays(xxlJobProperties.getLogRetentionDays());
        JobContext.initGroup();
        return jobSpringExecutor;
    }


}
