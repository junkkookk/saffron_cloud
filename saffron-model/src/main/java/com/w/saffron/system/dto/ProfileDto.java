package com.w.saffron.system.dto;
import lombok.Data;

import java.util.Date;

/**
 * @author w
 * @since 2023/3/22
 */
@Data
public class ProfileDto {

    Long id;
    String username;

    String email;

    Integer gender;

    String avatar;

    String birthdayStr;

    Date birthday;

    String mobile;

    Date lastTime;

}
