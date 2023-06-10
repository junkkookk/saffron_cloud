package com.w.saffron.application.sys.service;

import com.w.saffron.application.sys.domain.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

/**
 * @author w
 * @since 2023/4/3
 */
@Service
public class ConfigService {

    private final MongoTemplate mongoTemplate;

    @Autowired
    public ConfigService(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public Config getConfig(){
        return mongoTemplate.findById(1L, Config.class);
    }

    public void updateConfig(Config config){
        config.setId(1L);
        mongoTemplate.save(config);
    }





}
