package com.w.saffron.rpc.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.w.saffron.rpc.CustomDateFormat;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author w
 * @since 2023/6/13
 */
@Configuration
@EnableFeignClients(basePackages = "com.w.saffron.rpc")
public class FeignConfig {

    @Autowired
    private Jackson2ObjectMapperBuilder jackson2ObjectMapperBuilder;

    @Bean
    @ConditionalOnMissingBean
    public HttpMessageConverters messageConverters(ObjectProvider<HttpMessageConverter<?>> converters){
        return new HttpMessageConverters(converters.orderedStream().toList());
    }

    @Bean
    public MappingJackson2HttpMessageConverter MappingJsonpHttpMessageConverter() {
        ObjectMapper mapper = jackson2ObjectMapperBuilder.build();
        // ObjectMapper为了保障线程安全性，里面的配置类都是一个不可变的对象
        // 所以这里的setDateFormat的内部原理其实是创建了一个新的配置类
        DateFormat dateFormat = mapper.getDateFormat();
        mapper.setDateFormat(new CustomDateFormat(dateFormat));
        MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter(
                mapper);
        List<MediaType> mediaTypes = new ArrayList<>(mappingJackson2HttpMessageConverter.getSupportedMediaTypes());
        mediaTypes.add(MediaType.TEXT_PLAIN);
        mappingJackson2HttpMessageConverter.setSupportedMediaTypes(mediaTypes);
        return mappingJackson2HttpMessageConverter;
    }


}
