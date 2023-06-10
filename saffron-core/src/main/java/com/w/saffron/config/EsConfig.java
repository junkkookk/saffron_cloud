package com.w.saffron.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.config.EnableElasticsearchAuditing;
import org.springframework.data.elasticsearch.core.mapping.SimpleElasticsearchMappingContext;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

/**
 * @author w
 * @since 2023/4/13
 */
@Configuration
@EnableElasticsearchRepositories
@EnableElasticsearchAuditing
public class EsConfig {

    @Bean
    public SimpleElasticsearchMappingContext elasticsearchMappingContext(){
        return new SimpleElasticsearchMappingContext();
    }

}
