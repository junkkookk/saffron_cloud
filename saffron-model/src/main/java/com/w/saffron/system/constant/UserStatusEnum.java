package com.w.saffron.system.constant;

/**
 * @author w
 * @since 2023/3/22
 */
public enum UserStatusEnum {

    INACTIVE("未激活",0),
    ACTIVE("已激活",1),
    BAN("被禁用",2);

    final String label;

    public final Integer value;

    UserStatusEnum(String label, Integer value){
        this.label = label;
        this.value = value;
    }

    public static UserStatusEnum parse(Integer value){
        for (UserStatusEnum userStatusEnum : UserStatusEnum.values()) {
            if (userStatusEnum.value.equals(value)){
                return userStatusEnum;
            }
        }
        return null;
    }
}
