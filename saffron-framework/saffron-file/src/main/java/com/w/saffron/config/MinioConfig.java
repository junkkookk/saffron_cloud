package com.w.saffron.config;

import cn.hutool.extra.spring.SpringUtil;
import com.w.saffron.minio.MinioProperties;
import io.minio.MinioClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author w
 * @since 2023/3/29
 */
@Configuration
public class MinioConfig {

    public MinioProperties getMinioProperties(){
        MinioProperties properties = new MinioProperties();
        properties.setEndpoint(SpringUtil.getProperty("spring.minio.endpoint"));
        properties.setBucket(SpringUtil.getProperty("spring.minio.bucket"));
        properties.setAccessKey(SpringUtil.getProperty("spring.minio.accessKey"));
        properties.setSecretKey(SpringUtil.getProperty("spring.minio.secretKey"));
        return properties;
    }

    @Bean
    public MinioClient minioClient(){
        MinioProperties properties = getMinioProperties();
        return MinioClient.builder()
                .endpoint(properties.getEndpoint())
                .credentials(properties.getAccessKey(), properties.getSecretKey())
                .build();
    }

}
