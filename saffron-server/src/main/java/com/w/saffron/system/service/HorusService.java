package com.w.saffron.application.sys.service;

import cn.dev33.satoken.secure.SaSecureUtil;
import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.w.saffron.application.sys.constant.LoginTypeEnum;
import com.w.saffron.application.sys.domain.SocialUserRel;
import com.w.saffron.common.SaffronInfo;
import com.w.saffron.common.exception.OprException;
import com.w.saffron.common.utils.BeanUtil;
import com.w.saffron.common.utils.TemplateUtil;
import com.w.saffron.redis.RedisManager;
import com.w.saffron.system.bean.LoginBean;
import com.w.saffron.system.constant.*;
import com.w.saffron.system.domain.SocialUser;
import com.w.saffron.system.domain.User;
import com.w.saffron.system.service.SocialUserRelService;
import com.w.saffron.system.service.SocialUserService;
import com.w.saffron.system.service.UserService;
import com.xkcoding.http.config.HttpConfig;
import io.github.linpeilie.Converter;
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
import java.util.Date;
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
    private final Converter converter;

    @Autowired
    public HorusService(UserService userService,
                        SocialUserService socialUserService,
                       SocialUserRelService socialUserRelService,
                        RabbitTemplate rabbitTemplate,
                        Converter converter) {
        this.userService = userService;
        this.socialUserService = socialUserService;
        this.socialUserRelService = socialUserRelService;
        this.rabbitTemplate = rabbitTemplate;
        this.converter = converter;
    }

    public SaTokenInfo login(LoginBean loginBean){
        LoginTypeEnum loginTypeEnum = LoginTypeEnum.parse(loginBean.getLoginType());
        User findUser = null;
        switch (loginTypeEnum){
            case USERNAME_PASSWORD:
                String username = loginBean.getUsername();
                String password = loginBean.getPassword();
                if (StrUtil.isEmpty(username)){
                    throw new OprException("username is empty!");
                }
                if (StrUtil.isEmpty(password)){
                    throw new OprException("password is empty");
                }
                findUser = userService.findByUsername(username);
                String aesKey = SaffronInfo.getAesKey();
                String userPassword = findUser.getPassword();
                String encryptPassword = SaSecureUtil.aesEncrypt(aesKey,password);
                if (!StrUtil.equals(encryptPassword,userPassword)){
                    throw new OprException("password is incorrect!");
                }
                break;
            case EMAIL_CODE:
                String email = loginBean.getEmail();
                String code = loginBean.getCode();
                findUser = userService.findByEmail(email);
                String cacheCode = RedisManager.get(email);
                if (StrUtil.isEmpty(cacheCode)){
                    throw new OprException("code is expire! please generate new");
                }
                if (!StrUtil.equals(code,cacheCode)){
                    throw new OprException("code is incorrect!");
                }
                RedisManager.remove(email);
                break;
            case EMAIL_PASSWORD:
                throw new OprException("not support loginType");
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
        updateLastTime(findUser.getId());
        return StpUtil.getTokenInfo();
    }


    public ProfileBean getProfile() {
        Long id = StpUtil.getLoginIdAsLong();
        String key = MessageFormat.format(HorusCons.PROFILE_KEY,id);
        User profile = RedisManager.get(key, User.class);
        if (profile==null){
             profile = userService.findById(id);
             RedisManager.set(key,profile,1L, TimeUnit.HOURS);
        }
        return converter.convert(profile,ProfileBean.class);
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

    public void updateProfile(ProfileBean profileBean) {
        long id = StpUtil.getLoginIdAsLong();
        profileBean.setId(id);
        String avatar = profileBean.getAvatar();
        if (StrUtil.isNotEmpty(avatar)){
            profileBean.setAvatar(avatar);
        }else {
            profileBean.setAvatar(null);
        }
        String birthdayStr = profileBean.getBirthdayStr();
        if (StrUtil.isNotEmpty(birthdayStr)){
            DateTime time = DateUtil.parse(birthdayStr);
            Date date = time.toJdkDate();
            profileBean.setBirthday(date);
        }
        User user = converter.convert(profileBean,User.class);
        User oldUser = userService.findById(id);
        BeanUtil.copy(user, oldUser);
        userService.save(oldUser);
        RedisManager.remove(MessageFormat.format(HorusCons.PROFILE_KEY,id));
    }


    public void updateLastTime(Long userId){
        User user = userService.findById(userId);
        user.setLastTime(new Date());
        userService.save(user);
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
        MailMessageBean messageBean = new MailMessageBean("【登录验证码】", TemplateUtil.getMailCodeHtml(emailCode,"【登录验证码】"),email );
        rabbitTemplate.convertAndSend(MailCons.DIRECT_MAIL_QUEUE,messageBean);
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
