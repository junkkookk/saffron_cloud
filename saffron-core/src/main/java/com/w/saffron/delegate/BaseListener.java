package com.w.saffron.delegate;

import cn.hutool.extra.spring.SpringUtil;
import com.w.saffron.common.SaffronInfo;
import com.w.saffron.schdule.BaseJob;
import com.w.saffron.schdule.JobContext;
import com.w.saffron.schdule.JobManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

/**
 * @author w
 * @since 2023/6/12
 */
@Slf4j
@Component
public class BaseListener{

    @Autowired
    SaffronInfo saffronInfo;
    @EventListener
    public void onApplicationEvent(ContextRefreshedEvent event){
        log.info("Application: {} listening {}",
                SpringUtil.getProperty("spring.application.name"),
                SpringUtil.getProperty("server.port"));
        log.info("Context-path: {}",SpringUtil.getProperty("server.servlet.context-path"));
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
