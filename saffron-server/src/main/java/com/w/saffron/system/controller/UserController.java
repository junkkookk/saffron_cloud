package com.w.saffron.system.controller;


import com.w.saffron.common.PageResult;
import com.w.saffron.system.bean.UserBean;
import com.w.saffron.system.domain.User;
import com.w.saffron.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author w
 * @since 2023/3/22
 */
@RestController
@RequestMapping("user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("search")
    public R<PageResult<?>> findPage(Integer current, Integer pageSize, String searchText, UserBean userBean){
        return R.ok(userService.findPage(current,pageSize, searchText,userBean));
    }

    @GetMapping(value = "find-by-id")
    public R<User> findById(Long userId){
        return R.ok(userService.findById(userId));
    }


}
