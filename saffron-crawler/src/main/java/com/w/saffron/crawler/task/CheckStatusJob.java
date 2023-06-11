package com.w.saffron.crawler.task;

import cn.hutool.extra.spring.SpringUtil;
import com.w.saffron.common.PageParam;
import com.w.saffron.crawler.service.VideoService;
import com.w.saffron.crawler.service.ZmqService;
import com.w.saffron.schdule.BaseJob;
import com.w.saffron.schdule.ScheduleBuilder;
import com.w.saffron.video.bean.VideoRequest;
import com.w.saffron.video.constant.Status;
import com.w.saffron.video.domain.Video;
import com.xxl.job.core.biz.model.ReturnT;
import io.github.linpeilie.Converter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author w
 * @since 2023/6/6
 */
@Slf4j
public class CheckStatusJob extends BaseJob {
    @Override
    protected void config() {
        setStartNow(false);
        setScheduleBuilder(ScheduleBuilder.getEveryMinuteSchedule(60*24));
        setJobId("check_status");
        setDesc("检测视频状态");
    }

    @SneakyThrows
    @Override
    protected ReturnT<String> run() {
        log.info("开始检测视频状态");
        VideoService videoService = SpringUtil.getBean(VideoService.class);
        Converter converter = SpringUtil.getBean(Converter.class);
        ZmqService zmqService = SpringUtil.getBean(ZmqService.class);
        List<Video> videoList = videoService.findByStatus(Status.DEFAULT, PageParam.firstPage());
        for (Video video : videoList) {
            switch (video.getSource()) {
                case ZMQ -> {
                    String uuid = video.getUuid();
                    zmqService.findPlayUrlByUUid(uuid).ifPresent(playUrl->{
                        video.setPlayUrl(playUrl);
                        video.setStatus(Status.READY);
                        try {
                            videoService.saveOrUpdate(converter.convert(video, VideoRequest.SaveOrUpdate.class));
                            log.info("采集成功:{}",video.getTitle());
                        }catch (Exception e){
                            log.error("更新失败：{}-{}",video.getTitle(),e.getMessage());
                        }
                    });
                }
                case FOOT52 -> {

                }
                default -> {}
            }
        }
        return ReturnT.SUCCESS;
    }
}
