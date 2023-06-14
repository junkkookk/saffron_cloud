package com.w.saffron.schdule;

import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.json.JSONUtil;
import com.w.saffron.tool.SaffronInfo;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author w
 * @since 2023/4/27
 */
@UtilityClass
@Slf4j
public class JobManager {

    public void addJob(List<BaseJob> baseJobs){
        SaffronInfo saffronInfo = SpringUtil.getBean(SaffronInfo.class);
        if (!saffronInfo.getEnableJob()){
            log.error("未开启job, 任务添加失败");
            return;
        }
        if (baseJobs.isEmpty()){
            return;
        }
        for (BaseJob baseJob : baseJobs) {
            JobContext.addJob(baseJob);
        }
    }

    public void addJob(BaseJob baseJob){
        addJob(List.of(baseJob));
    }


}
