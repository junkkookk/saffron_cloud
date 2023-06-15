package com.w.saffron.crawler.task;

import cn.hutool.extra.spring.SpringUtil;
import com.w.saffron.crawler.service.ZmqService;
import com.w.saffron.rpc.server.v100.VideoInterface;
import com.w.saffron.schdule.BaseJob;
import com.w.saffron.schdule.ScheduleBuilder;
import com.w.saffron.video.constant.Status;
import com.w.saffron.video.dto.VideoDto;
import com.w.saffron.video.dto.VideoSearchDto;
import com.w.saffron.video.vo.VideoVo;
import com.xxl.job.core.biz.model.ReturnT;
import io.github.linpeilie.Converter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
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
        VideoInterface videoInterface = SpringUtil.getBean(VideoInterface.class);
        Converter converter = SpringUtil.getBean(Converter.class);
        ZmqService zmqService = SpringUtil.getBean(ZmqService.class);
        List<VideoVo> videoList = Collections.emptyList();
        try {
            videoList = videoInterface.search(VideoSearchDto.builder()
                            .status(Status.DEFAULT).build()).getData().getContent();
        } catch (Exception e) {
            log.info("获取视频列表错误", e);
        }
        for (VideoVo video : videoList) {
            switch (video.getSource()) {
                case ZMQ -> {
                    String uuid = video.getUuid();
                    try {
                        zmqService.findPlayUrlByUUid(uuid).ifPresent(playUrl -> {
                            video.setPlayUrl(playUrl);
                            video.setStatus(Status.READY);
                        });
                        log.info("{},采集成功",video.getTitle());
                    } catch (Exception e) {
                        log.error("{},采集失败:{}", video.getTitle(), e.getMessage());
                        video.setStatus(Status.ERROR);
                    }
                    videoInterface.updateVideo(converter.convert(video, VideoDto.class));
                }
                case FOOT52 -> {
                    log.info("暂不支持该来源");
                }
                default -> {
                    log.info("未知来源");
                }
            }
        }
        log.info("采集完成");
        return ReturnT.SUCCESS;
    }
}
