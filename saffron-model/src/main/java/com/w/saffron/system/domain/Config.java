package com.w.saffron.application.sys.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;


/**
 * @author w
 * @since 2023/4/4
 */
@Data
@Document(collation = "config")
public class Config {

    @Id
    private Long id;

    @Field("app_name")
    private String appName;

    @Field("app_desc")
    private String appDesc;

    @Field("bg")
    private String backgroundImage;


}
