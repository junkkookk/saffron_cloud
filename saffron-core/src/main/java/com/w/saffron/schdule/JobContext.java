package com.w.saffron.schdule;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.w.saffron.common.SaffronInfo;
import com.xxl.job.core.executor.XxlJobExecutor;
import lombok.extern.slf4j.Slf4j;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author w
 * @since 2023/4/27
 */
@Slf4j
public class JobContext {

    public static int groupId;

    public static void addJob(BaseJob baseJob){
        baseJob.config();
        log.info("Add Job {}", JSONUtil.toJsonStr(baseJob));
        JobInfo jobInfo = baseJob.getJobInfo();
        String jobId = jobInfo.getJobId();
        String desc = jobInfo.getDesc();
        String cron = baseJob.getCron();
        if (StrUtil.isBlank(jobId)){
            log.error("jobId is empty");
        }
        if (StrUtil.isBlank(desc)){
            log.error("desc is empty");
        }
        if (StrUtil.isBlank(cron)){
            log.error("cron is empty");
        }
        Set<String> jobs = XxlClient.getJobsByGroup(groupId).stream().map(XxlJobInfo::getExecutorHandler).collect(Collectors.toSet());
        if(!jobs.contains(jobId)){
            XxlClient.createJobInfo(groupId, baseJob);
        }
        XxlJobExecutor.registJobHandler(jobId,baseJob);
    }


    public static void initGroup(){
        XxlJobGroup xxlJobGroup = XxlClient.findJobGroupByAppName(SaffronInfo.getAppName());
        log.info("Init JobGroup {}",JSONUtil.toJsonStr(xxlJobGroup));
        if (xxlJobGroup!=null){
            groupId = xxlJobGroup.getId();
        }else {
            xxlJobGroup = new XxlJobGroup();
            xxlJobGroup.setAppname(SaffronInfo.getAppName());
            xxlJobGroup.setAddressType(0);
            xxlJobGroup.setTitle(SaffronInfo.getAppName());
            groupId = XxlClient.createGroup(xxlJobGroup);
        }
    }

}
