package com.w.saffron.schdule;

import lombok.Data;

/**
 * @author w
 * @since 2023/4/27
 */
@Data
public class JobInfo {

    private String jobId;

    private String desc;

    private String author;

    private BlockStrategy blockStrategy;

    private RouteStrategy routeStrategy;
    private MisfireStrategy misfireStrategy;

    private Integer failRetryCount;

    private String param;

    public JobInfo(){
        this.author = "Saffron";
        this.blockStrategy = BlockStrategy.SERIAL_EXECUTION;
        this.routeStrategy = RouteStrategy.FAILOVER;
        this.misfireStrategy = MisfireStrategy.DO_NOTHING;
        this.failRetryCount = 0;
    }

}
