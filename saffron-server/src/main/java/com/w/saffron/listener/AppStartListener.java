package com.w.saffron.listener;

import com.w.saffron.schdule.JobManager;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author w
 * @since 2023/3/13
 */
@Slf4j
@Component
public class AppStartListener implements ApplicationListener<ApplicationStartedEvent> {

    @Override
    public void onApplicationEvent(@NotNull ApplicationStartedEvent event) {
        initJob();
    }

    private void initJob() {
        JobManager.addJob(List.of(
        ));
    }
}
