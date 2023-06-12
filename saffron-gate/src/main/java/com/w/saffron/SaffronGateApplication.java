package com.w.saffron;

import com.w.saffron.config.AmqpConfig;
import com.w.saffron.config.JpaConfig;
import com.w.saffron.config.MinioConfig;
import com.w.saffron.config.MvcConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;

/**
 * @author w
 * @since 2023/6/12
 */
@SpringBootApplication(
        exclude = {DataSourceAutoConfiguration.class,
                JpaRepositoriesAutoConfiguration.class,
                HibernateJpaAutoConfiguration.class}
)
@ComponentScan(basePackages = "com.w.saffron",excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,classes = {
                JpaConfig.class,
                MvcConfig.class,
                MinioConfig.class,
                AmqpConfig.class,

        }),
        @ComponentScan.Filter(type = FilterType.ANNOTATION,classes = {
                Controller.class, ControllerAdvice.class
        })
})
public class SaffronGateApplication {
    public static void main(String[] args) {
        SpringApplication.run(SaffronGateApplication.class, args);
    }

}
