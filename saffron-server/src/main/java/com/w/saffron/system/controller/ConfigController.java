package com.w.saffron.system.controller;

import com.w.saffron.application.sys.domain.Config;
import com.w.saffron.application.sys.service.ConfigService;
import com.w.saffron.common.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author w
 * @since 2023/3/26
 */
@RestController
@RequestMapping("config")
public class ConfigController {

    private final ConfigService configService;

    @Autowired
    public ConfigController(ConfigService configService) {
        this.configService = configService;
    }

    @GetMapping("get")
    public R<?> getConfig(){
        return R.ok(configService.getConfig());
    }

    @PatchMapping("update")
    public R<?> update(Config config){
        configService.updateConfig(config);
        return R.ok();
    }

}
