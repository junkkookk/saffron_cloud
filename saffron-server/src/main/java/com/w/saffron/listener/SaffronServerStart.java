package com.w.saffron.listener;

import com.w.saffron.delegate.ApplicationStart;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author w
 * @since 2023/3/13
 */
@Slf4j
@Component
public class SaffronServerStart implements ApplicationStart {
    @Override
    public void init() {
    }

}
