package com.w.saffron.listener;

import com.w.saffron.crawler.task.CheckStatusJob;
import com.w.saffron.crawler.task.FetchZmqJob;
import com.w.saffron.crawler.task.TestJob;
import com.w.saffron.delegate.ApplicationStart;
import com.w.saffron.schdule.BaseJob;
import com.w.saffron.schdule.JobManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author w
 * @since 2023/3/13
 */
@Slf4j
@Component
public class SaffronCrawlerStart implements ApplicationStart {

    @Override
    public void init() {
        List<BaseJob> baseJobs = List.of(
                new FetchZmqJob(),
                new CheckStatusJob(),
                new TestJob()
        );

        JobManager.addJob(baseJobs);
    }
}
