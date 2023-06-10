package com.w.saffron.system.bean;

import lombok.Data;

/**
 * @author w
 * @since 2023/3/29
 */
@Data
public class FileBean {

    private String name;

    private boolean isDir;

    private Long size;

    private String fullName;

}
