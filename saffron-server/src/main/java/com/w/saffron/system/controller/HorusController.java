package com.w.saffron.system.controller;

import com.w.saffron.application.sys.service.HorusService;
import com.w.saffron.common.R;
import com.w.saffron.system.bean.LoginBean;
import com.w.saffron.system.constant.ProfileBean;
import jakarta.servlet.http.HttpServletResponse;
import me.zhyd.oauth.model.AuthCallback;
import me.zhyd.oauth.request.AuthRequest;
import me.zhyd.oauth.utils.AuthStateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * @author w
 * @since 2023/3/22
 */
@RestController
@RequestMapping
public class HorusController {

    private final HorusService horusService;

    @Autowired
    public HorusController(HorusService horusService) {
        this.horusService = horusService;
    }

    @PostMapping("login")
    public R<?> login(LoginBean loginBean){
        return R.ok(horusService.login(loginBean));
    }

    @GetMapping("logout")
    public R<?> logout(){
        horusService.logout();
        return R.ok();
    }

    @GetMapping("profile")
    public R<?> getProfile(){
        return R.ok(horusService.getProfile());
    }

    @PatchMapping ("profile")
    public R<?> updateProfile(ProfileBean profileBean){
        horusService.updateProfile(profileBean);
        return R.ok();
    }

    @RequestMapping("oauth/render/{source}")
    public void renderOauth(@PathVariable String source, HttpServletResponse response) throws IOException {
        AuthRequest authRequest = horusService.getAuthRequest(source);
        response.sendRedirect(authRequest.authorize(AuthStateUtils.createState()));
    }

    @RequestMapping("oauth/callback/{source}")
    @CrossOrigin
    public R<?> callback(AuthCallback callback,@PathVariable String source){
        return R.ok(horusService.oauthLogin(callback,source));
    }


    @GetMapping("mail/get-captcha")
    public R<?> generateEmailCode(String email){
        horusService.generateEmailCode(email);
        return R.ok();
    }


}
