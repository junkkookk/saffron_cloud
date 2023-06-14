package com.w.saffron.listener;

import com.w.saffron.delegate.ApplicationStart;
import com.w.saffron.schdule.JobContext;
import com.w.saffron.tool.SaffronInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author w
 * @since 2023/6/14
 */
@Component
@Slf4j
@Order(-1)
public class XxlJobStart implements ApplicationStart {
    @Autowired
    SaffronInfo saffronInfo;

    @Override
    public void init() {
        Boolean enableJob = saffronInfo.getEnableJob();
        if (enableJob!=null&&enableJob){
            log.info("Starting int jobGroup...");
            try {
                JobContext.initGroup();
            }catch (Exception e){
                log.error("Init jobGroup error {}",e.getMessage());
            }

        }
    }
}
