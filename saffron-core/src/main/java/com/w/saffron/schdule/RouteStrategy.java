package com.w.saffron.schdule;

/**
 * @author w
 * @since 2023/4/29
 */
public enum RouteStrategy {
    ROUND,
    RANDOM,
    FAILOVER,
    BUSYOVER,
    SHARDING_BROADCAST;

    private RouteStrategy() {
    }
}
