package com.w.saffron.common.constant;

import lombok.Getter;

/**
 * @author w
 * @since 2023/3/14
 */
@Getter
public enum JobEnum {

    CLEAN_TEMP("clean_temp", "清理临时文件任务"),
    CRAWLER_REAL_VIDEO("crawler_video", "爬取视频真实地址任务"),
    ERROR_RETRY("error_retry", "错误重试任务"),
    FETCH_52_NEW("fetch_52_video", "爬取52增量任务"),
    FETCH_NEW("fetch_new", "爬取增量视频任务"),
    HANDLE_VIDEO("handle_video", "处理视频缩略图任务"),
    LABEL_VIDEO("label_video", "清理临时文件任务"),
    TRANSFORM_VIDEO("transform_video", "原始视频转换任务");

    final String jobId;
    final String jobDesc;

    JobEnum(String jobId, String jobDesc) {
        this.jobId = jobId;
        this.jobDesc = jobDesc;
    }
}
