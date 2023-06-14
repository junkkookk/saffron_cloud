package com.w.saffron.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author w
 * @since 2023/3/30
 */
@Configuration
@Slf4j
public class AmqpConfig {

    @Bean
    public RabbitTemplate template(CachingConnectionFactory cachingConnectionFactory) {
        cachingConnectionFactory.setPublisherReturns(true);
        RabbitTemplate rabbitTemplate = new RabbitTemplate(cachingConnectionFactory);
        rabbitTemplate.setMandatory(true);
        rabbitTemplate.setConfirmCallback((data,ack,cause)-> log.info("消息发送成功====data:{},ack:{},cause:{}",data,ack,cause));
        rabbitTemplate.setReturnsCallback(returnedMessage -> log.info("消息丢失:"+returnedMessage.getMessage().toString()));
        return rabbitTemplate;
    }



}
