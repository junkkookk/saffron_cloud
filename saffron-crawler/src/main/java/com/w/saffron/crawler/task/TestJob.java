package com.w.saffron.crawler.task;

import cn.hutool.extra.spring.SpringUtil;
import com.w.saffron.rpc.server.v100.VideoInterface;
import com.w.saffron.schdule.BaseJob;
import com.w.saffron.schdule.ScheduleBuilder;
import com.w.saffron.video.dto.VideoDto;
import com.xxl.job.core.biz.model.ReturnT;

/**
 * @author w
 * @since 2023/6/13
 */
public class TestJob extends BaseJob {
    @Override
    protected void config() {
        setStartNow(false);
        setScheduleBuilder(ScheduleBuilder.getEveryMinuteSchedule(60*24));
        setJobId("TEST");
        setDesc("测试任务");
    }

    @Override
    protected ReturnT<String> run() {
        VideoInterface videoInterface = SpringUtil.getBean(VideoInterface.class);
        try {
            System.out.println(videoInterface.addVideo(
                    VideoDto.builder()
                            .uuid("aaa")
                            .build()
            ));
        }catch (Exception e){
            e.printStackTrace();
        }
        return ReturnT.SUCCESS;
    }
}
