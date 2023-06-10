package com.w.saffron.schdule;

import lombok.experimental.UtilityClass;

import java.util.List;

/**
 * @author w
 * @since 2023/4/27
 */
@UtilityClass
public class JobManager {

    public void addJob(List<BaseJob> baseJobs){
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
