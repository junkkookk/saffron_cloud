package com.w.saffron.system.constant;

import lombok.Getter;

/**
 * @author w
 * @since 2023/3/22
 */
@Getter
public enum LoginTypeEnum {
    USERNAME_PASSWORD("account",1),
    EMAIL_CODE("email",2),
    EMAIL_PASSWORD("email-password",3);
    final String label;
    final Integer value;

    LoginTypeEnum(String label, Integer value) {
        this.label = label;
        this.value = value;
    }

    public static LoginTypeEnum parse(Integer value){
        for (LoginTypeEnum loginTypeEnum : LoginTypeEnum.values()) {
            if (loginTypeEnum.value.equals(value)){
                return loginTypeEnum;
            }
        }
        return USERNAME_PASSWORD;
    }

}
