package com.w.saffron.listener;

import com.w.saffron.crawler.task.CheckStatusJob;
import com.w.saffron.crawler.task.FetchZmqJob;
import com.w.saffron.delegate.ApplicationStart;
import com.w.saffron.schdule.BaseJob;
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
    public List<BaseJob> getInitJobs() {
        return List.of(
                new FetchZmqJob(),
                new CheckStatusJob()
        );
    }

    @Override
    public void init() {

    }
}
