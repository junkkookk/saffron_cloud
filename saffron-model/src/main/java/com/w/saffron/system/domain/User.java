package com.w.saffron.system.domain;

import com.w.saffron.common.BaseEntity;
import com.w.saffron.system.bean.UserBean;
import com.w.saffron.system.constant.ProfileBean;
import io.github.linpeilie.annotations.AutoMapper;
import io.github.linpeilie.annotations.AutoMappers;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @author w
 * @since 2023/3/22
 */
@Entity
@Table(name = "sys_user")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Accessors(chain = true)
@AutoMappers({
        @AutoMapper(target = ProfileBean.class),
        @AutoMapper(target = UserBean.class)
})
public class User extends BaseEntity {

    private String username;

    private String password;

    private String email;

    private String avatar;

    private Integer gender;

    private Integer status;

    private Date birthday;

    private Date lastTime;

}
