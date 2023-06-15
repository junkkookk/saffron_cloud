package com.w.saffron.system.vo;

import lombok.Data;

import java.util.Date;

/**
 * @author w
 * @since 2023/6/15
 */
@Data
public class ProfileVo {
    private Long id;
    private String username;
    private String email;
    private String avatar;
    private Integer gender;
    private Date lastTime;

}
