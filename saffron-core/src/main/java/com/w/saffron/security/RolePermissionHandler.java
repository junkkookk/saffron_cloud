package com.w.saffron.security;

import cn.dev33.satoken.stp.StpInterface;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author w
 * @since 2023/3/21
 */
@Component
public class RolePermissionHandler implements StpInterface {
    @Override
    public List<String> getPermissionList(Object o, String s) {
        return null;
    }

    @Override
    public List<String> getRoleList(Object o, String s) {
        return null;
    }
}
