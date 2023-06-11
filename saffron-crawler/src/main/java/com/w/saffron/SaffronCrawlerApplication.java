package com.w.saffron;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * @author w
 * @since 2023/6/10
 */
@SpringBootApplication
public class SaffronCrawlerApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(SaffronCrawlerApplication.class);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(SaffronCrawlerApplication.class);
    }
}
