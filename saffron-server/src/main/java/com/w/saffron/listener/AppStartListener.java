package com.w.saffron.listener;

import com.w.saffron.application.sys.domain.Config;
import com.w.saffron.schdule.JobManager;
import com.w.saffron.video.task.CheckStatusJob;
import com.w.saffron.video.task.FetchZmqJob;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author w
 * @since 2023/3/13
 */
@Slf4j
@Component
public class AppStartListener implements ApplicationListener<ApplicationStartedEvent> {

    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public void onApplicationEvent(@NotNull ApplicationStartedEvent event) {
        initJob();
        initConfig();
    }

    private void initJob() {
        JobManager.addJob(List.of(
                new FetchZmqJob(),
                new CheckStatusJob()
        ));
    }

    public void initConfig(){
        Config config = mongoTemplate.findById(1L, Config.class);
        if (config==null){
            config = new Config();
            config.setId(1L);
            config.setBackgroundImage("/saffron_files/background.jpg");
            config.setAppName("saffron");
            config.setAppDesc("a saffron app");
            mongoTemplate.save(config);
        }
    }
}
