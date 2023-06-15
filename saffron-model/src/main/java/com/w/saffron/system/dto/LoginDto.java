package com.w.saffron.system.dto;

import lombok.Data;

import jakarta.validation.constraints.NotNull;

/**
 * @author w
 * @since 2023/3/22
 */
@Data
public class LoginDto {

    private String username;
    @NotNull(message = "密码不能为空")
    private String password;
    private String email;
    private String code;
    @NotNull(message = "登录方式不能为空")
    private Integer loginType;
    private Boolean rememberMe;

}
