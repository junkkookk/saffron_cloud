package com.w.saffron.system.constant;

import com.w.saffron.system.domain.User;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.util.Date;

/**
 * @author w
 * @since 2023/3/22
 */
@Data
@AutoMapper(target = User.class)
public class ProfileBean {

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
