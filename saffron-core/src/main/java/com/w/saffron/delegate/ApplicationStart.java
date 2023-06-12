package com.w.saffron.delegate;

import com.w.saffron.schdule.BaseJob;

import java.util.Collections;
import java.util.List;

/**
 * @author w
 * @since 2023/6/12
 */
public interface ApplicationStart {

    void init();

    default List<BaseJob> getInitJobs(){
        return Collections.emptyList();
    };

}
