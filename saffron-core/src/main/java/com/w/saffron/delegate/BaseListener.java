package com.w.saffron.delegate;

import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.w.saffron.common.SaffronInfo;
import com.w.saffron.schdule.BaseJob;
import com.w.saffron.schdule.JobContext;
import com.w.saffron.schdule.JobManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

/**
 * @author w
 * @since 2023/6/12
 */
@Slf4j
@Component
public class BaseListener implements CommandLineRunner {

    @Autowired
    SaffronInfo saffronInfo;

    @Override
    public void run(String... args) throws Exception {
        log.info("Application: {} listening {}",
                SpringUtil.getProperty("spring.application.name"),
                SpringUtil.getProperty("server.port"));
        String contextPath = SpringUtil.getProperty("server.servlet.context-path");
        if (StrUtil.isEmpty(contextPath)){
            contextPath = SpringUtil.getProperty("server.webflux.base-path");
        }
        log.info("Context-path: {}", contextPath);
        Collection<ApplicationStart> applicationStarts = SpringUtil.getBeansOfType(ApplicationStart.class).values();
        if (saffronInfo.getEnableListener()){
            applicationStarts.forEach(ApplicationStart::init);
        }
        if (saffronInfo.getEnableJob()){
            JobContext.initGroup();
            applicationStarts.forEach(applicationStart -> {
                List<BaseJob> jobList = applicationStart.getInitJobs();
                jobList.forEach(JobManager::addJob);
            });
        }
    }
}
