package com.w.saffron.system.service;

import cn.dev33.satoken.secure.SaSecureUtil;
import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;

import com.w.saffron.cache.RedisManager;
import com.w.saffron.covert.UserConverter;
import com.w.saffron.dto.MailMessageDto;
import com.w.saffron.exception.OprException;
import com.w.saffron.queue.MailQueue;
import com.w.saffron.system.constant.HorusCons;
import com.w.saffron.system.constant.LoginTypeEnum;
import com.w.saffron.system.constant.UserStatusEnum;
import com.w.saffron.system.dto.LoginDto;
import com.w.saffron.system.dto.ProfileDto;
import com.w.saffron.system.domain.SocialUser;
import com.w.saffron.system.domain.SocialUserRel;
import com.w.saffron.system.domain.User;
import com.w.saffron.system.vo.ProfileVo;
import com.w.saffron.utils.TemplateUtil;
import com.xkcoding.http.config.HttpConfig;
import me.zhyd.oauth.config.AuthConfig;
import me.zhyd.oauth.model.AuthCallback;
import me.zhyd.oauth.model.AuthUser;
import me.zhyd.oauth.request.AuthGiteeRequest;
import me.zhyd.oauth.request.AuthGithubRequest;
import me.zhyd.oauth.request.AuthRequest;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.text.MessageFormat;
import java.time.Duration;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author w
 * @since 2023/3/22
 */
@Service
public class HorusService {

    private final UserService userService;

    private final SocialUserService socialUserService;
    private final SocialUserRelService socialUserRelService;

    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public HorusService(UserService userService,
                        SocialUserService socialUserService,
                       SocialUserRelService socialUserRelService,
                        RabbitTemplate rabbitTemplate) {
        this.userService = userService;
        this.socialUserService = socialUserService;
        this.socialUserRelService = socialUserRelService;
        this.rabbitTemplate = rabbitTemplate;
    }
    public SaTokenInfo login(LoginDto loginDto){
        LoginTypeEnum loginTypeEnum = LoginTypeEnum.parse(loginDto.getLoginType());
        User findUser = null;
        switch (loginTypeEnum) {
            case USERNAME_PASSWORD -> {
                String username = loginDto.getUsername();
                String password = loginDto.getPassword();
                if (StrUtil.isEmpty(username)) {
                    throw new OprException("username is empty!");
                }
                if (StrUtil.isEmpty(password)) {
                    throw new OprException("password is empty");
                }
                findUser = userService.findByUsername(username);
                String aesKey = "saffron";
                String userPassword = findUser.getPassword();
                String encryptPassword = SaSecureUtil.aesEncrypt(aesKey, password);
                if (!StrUtil.equals(encryptPassword, userPassword)) {
                    throw new OprException("password is incorrect!");
                }
            }
            case EMAIL_CODE -> {
                String email = loginDto.getEmail();
                String code = loginDto.getCode();
                findUser = userService.findByEmail(email);
                String cacheCode = RedisManager.get(email);
                if (StrUtil.isEmpty(cacheCode)) {
                    throw new OprException("code is expire! please generate new");
                }
                if (!StrUtil.equals(code, cacheCode)) {
                    throw new OprException("code is incorrect!");
                }
                RedisManager.remove(email);
            }
            case EMAIL_PASSWORD -> throw new OprException("not support loginType");
        }
        if (findUser==null){
            throw new OprException("login error!");
        }
        UserStatusEnum userStatusEnum = UserStatusEnum.parse(findUser.getStatus());
        if (userStatusEnum==UserStatusEnum.INACTIVE){
            throw new OprException("not active");
        }
        if (userStatusEnum==UserStatusEnum.BAN){
            throw new OprException("user is ban");
        }
        StpUtil.login(findUser.getId(),loginTypeEnum.getLabel());
        return StpUtil.getTokenInfo();
    }


    public ProfileVo getProfile() {
        Long id = StpUtil.getLoginIdAsLong();
        String key = MessageFormat.format(HorusCons.PROFILE_KEY,id);
        ProfileVo profile = RedisManager.get(key, ProfileVo.class);
        if (profile==null){
             User user = userService.findById(id);
             if (user==null){
                 throw new OprException("异常用户");
             }
             profile = UserConverter.toUserProfileVo(user);
             RedisManager.set(key,profile,1L, TimeUnit.HOURS);
        }
        return profile;
    }

    public SaTokenInfo oauthLogin(AuthCallback callback,String source) {
        AuthRequest authRequest = getAuthRequest(source);
        AuthUser authUser;
        try {
            authUser = (AuthUser) authRequest.login(callback).getData();
        } catch (Exception e) {
            throw new OprException(e.getMessage());
        }
        String uuid = authUser.getUuid();
        source = authUser.getSource();
        SocialUser socialUser = socialUserService.findByUuidAndSource(uuid, source);
        Long userId;
        if (socialUser == null) {
            socialUser = SocialUser.builder()
                    .uuid(uuid)
                    .source(source)
                    .accessToken(authUser.getToken().getAccessToken())
                    .build();
            User user = User.builder()
                    .status(UserStatusEnum.ACTIVE.value)
                    .avatar(authUser.getAvatar())
                    .email(authUser.getEmail())
                    .username(authUser.getUsername())
                    .build();
            user = userService.save(user);
            socialUser = socialUserService.save(socialUser);
            socialUserRelService.save(SocialUserRel.builder()
                    .userId(user.getId())
                    .socialUserId(socialUser.getId())
                    .build());
            userId = user.getId();
        } else {
            SocialUserRel socialUserRel = socialUserRelService.findBySocialUserId(socialUser.getId());
            userId = socialUserRel.getUserId();
        }
        StpUtil.login(userId);
        return StpUtil.getTokenInfo();
    }

    public AuthRequest getAuthRequest(String source) {
        switch (source){
            case "github":
                return new AuthGithubRequest(AuthConfig.builder()
                        .clientId("c42f03d296ddabc11823")
                        .clientSecret("aa4b83bb9338b18cc6b0d4899935162e25608149")
                        .redirectUri("http://localhost:8871/saffron/oauth/callback/github")
                        .httpConfig(HttpConfig.builder()
                                .proxy(new Proxy(Proxy.Type.HTTP,new InetSocketAddress("127.0.0.1",33210)))
                                .build())
                        .build());
            case "gitee":
                return new AuthGiteeRequest(AuthConfig.builder()
                        .clientId("65f8053167f3b860781f5ed8a50f0bd6f4a0aac21c972c18229dd97ca65600c0")
                        .clientSecret("a295d80c0e64d66d9a4385436ebd914578a6ed3b5e591b58e17b26a549386c3c")
                        .redirectUri("http://localhost:3000/login")
                        .build());
        }
        return null;
    }

    public void generateEmailCode(String email) {
        if (!isValid(email)){
            throw new OprException("邮件地址不合法");
        }
        User user = userService.findByEmail(email);
        if (user == null){
            throw new OprException("该邮箱并未绑定用户");
        }
        String emailCode = RandomUtil.randomNumbers(6);
        RedisManager.set(email, emailCode, Duration.ofMinutes(5L));
        MailMessageDto messageDto = new MailMessageDto("【登录验证码】", TemplateUtil.getMailCodeHtml(emailCode,"【登录验证码】"),email );
        rabbitTemplate.convertAndSend(MailQueue.DIRECT_MAIL_QUEUE,messageDto);
    }

    private boolean isValid(String email) {
        // 定义邮箱地址的正则表达式
        String regex = "^([a-zA-Z0-9_\\.-]+)@([\\da-z\\.-]+)\\.([a-z\\.]{2,6})$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public void logout() {
        Long id = StpUtil.getLoginIdAsLong();
        String key = MessageFormat.format(HorusCons.PROFILE_KEY,id);
        RedisManager.remove(key);
        StpUtil.logout();
    }
}
