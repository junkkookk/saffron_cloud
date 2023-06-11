package com.w.saffron.crawler.config;


import com.w.saffron.crawler.interfaces.ZmqApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;

/**
 * @author w
 * @since 2023/6/9
 */
@Configuration
public class ApiConfig {


    @Bean
    ZmqApi zmqApi(){
        HttpClient httpClient = HttpClient.create()
                .responseTimeout(Duration.ofSeconds(5));
        WebClient webClient = WebClient.builder()
                .baseUrl("https://a1.zimu73.com/api/resource")
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .build();
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builder(WebClientAdapter.forClient(webClient)).build();
        return factory.createClient(ZmqApi.class);
    }


}
