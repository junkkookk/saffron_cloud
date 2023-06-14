package com.w.saffron.delegate;

import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.w.saffron.tool.SaffronInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author w
 * @since 2023/6/12
 */
@Slf4j
@Component
public class BaseListener implements CommandLineRunner {
    @Autowired
    SaffronInfo saffronInfo;
    @Override
    public void run(String... args) throws Exception {
        log.info("Application: {} listening {}",
                SaffronInfo.getAppName(),
                SpringUtil.getProperty("server.port"));
        String contextPath = SpringUtil.getProperty("server.servlet.context-path");
        if (StrUtil.isEmpty(contextPath)){
            contextPath = SpringUtil.getProperty("server.webflux.base-path");
        }
        log.info("Context-path: {}", contextPath);
        Collection<ApplicationStart> applicationStarts = SpringUtil.getBeansOfType(ApplicationStart.class).values();
        Boolean enableListener = saffronInfo.getEnableListener();
        if (enableListener!=null&&enableListener){
            List<ApplicationStart> applicationStartList = new ArrayList<>(applicationStarts);
            applicationStartList.sort(AnnotationAwareOrderComparator.INSTANCE);
            applicationStartList.forEach(ApplicationStart::init);
        }
        log.info(SaffronInfo.getAppName()+"  started");
    }
}
