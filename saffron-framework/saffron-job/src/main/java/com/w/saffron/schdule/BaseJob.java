package com.w.saffron.schdule;

import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * @author w
 * @since 2023/4/27
 */
@Getter
@Setter
@Slf4j
public abstract class BaseJob extends IJobHandler {

    private boolean startNow;

    protected JobInfo jobInfo = new JobInfo();

    private ScheduleBuilder scheduleBuilder;

    protected abstract void config();

    protected abstract ReturnT<String> run();

    @Override
    public void execute(){
        this.run();
    }

    public String getCron(){
        return scheduleBuilder.getSchedule();
    }

    public void setJobId(String jobId){
        this.jobInfo.setJobId(jobId);
    }

    public void setDesc(String desc){
        this.jobInfo.setDesc(desc);
    }

}
