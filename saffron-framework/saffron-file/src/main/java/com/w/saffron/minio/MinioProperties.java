package com.w.saffron.minio;

import lombok.Data;

/**
 * @author w
 * @since 2023/3/29
 */
@Data
public class MinioProperties {
    private String endpoint;

    private String contextUrl;

    private String bucket;

    private String accessKey;

    private String secretKey;
}
