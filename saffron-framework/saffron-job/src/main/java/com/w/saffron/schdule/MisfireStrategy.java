package com.w.saffron.schdule;


/**
 * @author xuxueli 2020-10-29 21:11:23
 */
public enum MisfireStrategy {

    /**
     * do nothing
     */
    DO_NOTHING,

    /**
     * fire once now
     */
    FIRE_ONCE_NOW;

    MisfireStrategy() {
    }

}
