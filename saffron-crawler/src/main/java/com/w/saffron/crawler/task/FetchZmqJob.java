package com.w.saffron.crawler.task;

import cn.hutool.extra.spring.SpringUtil;
import com.w.saffron.crawler.service.VideoService;
import com.w.saffron.crawler.service.ZmqService;
import com.w.saffron.schdule.BaseJob;
import com.w.saffron.schdule.ScheduleBuilder;
import com.w.saffron.video.bean.VideoRequest;
import com.xxl.job.core.biz.model.ReturnT;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author w
 * @since 2023/6/6
 */
@Slf4j
public class FetchZmqJob extends BaseJob {
    @Override
    protected void config() {
        setStartNow(false);
        setScheduleBuilder(ScheduleBuilder.getEveryMinuteSchedule(60*24));
        setJobId("crawler_zmq");
        setDesc("爬取zmq视频");
    }

    @SneakyThrows
    @Override
    protected ReturnT<String> run() {
        log.info("开始爬取zmq视频");
        ZmqService zmqService = SpringUtil.getBean(ZmqService.class);
        VideoService videoService = SpringUtil.getBean(VideoService.class);
        int page = 1;
        boolean hasMore = true;
        int totalCount=0;
        while (hasMore){
            int count = 0;
            List<VideoRequest.SaveOrUpdate> videoReqList = zmqService.findByPage(page);
            page++;
            for (VideoRequest.SaveOrUpdate saveOrUpdate : videoReqList) {
                try {
                    videoService.saveOrUpdate(saveOrUpdate);
                    log.info("入库成功"+saveOrUpdate.getUuid());
                    count++;
                }catch (Exception e){
                    log.error("入库出错："+ e.getMessage());
                }
            }
            if (count<24){
                hasMore = false;
            }
            totalCount = totalCount + count;
        }
        ReturnT<String> success = ReturnT.SUCCESS;
        String msg = "本次共爬取" + totalCount + "个视频";
        success.setMsg(msg);
        log.info(msg);
        return success;
    }
}
