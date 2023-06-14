package com.w.saffron.schdule;

/**
 * @author w
 * @since 2023/4/29
 */
public enum BlockStrategy {
    SERIAL_EXECUTION,
    DISCARD_LATER,
    COVER_EARLY;

    private BlockStrategy() {
    }
}