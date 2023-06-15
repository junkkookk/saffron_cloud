package com.w.saffron.system.domain;

import com.w.saffron.common.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
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
