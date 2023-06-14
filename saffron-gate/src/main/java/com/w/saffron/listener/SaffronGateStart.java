package com.w.saffron.listener;

import com.w.saffron.delegate.ApplicationStart;
import com.w.saffron.rpc.server.v100.UserInterface;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author w
 * @since 2023/6/12
 */
@Slf4j
@Component
public class SaffronGateStart implements ApplicationStart {

    @Autowired
    UserInterface userInterface;

    @Override
    public void init() {
    }
}
