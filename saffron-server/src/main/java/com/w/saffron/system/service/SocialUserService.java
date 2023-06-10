package com.w.saffron.system.service;

import com.w.saffron.system.dao.SocialUserDao;
import com.w.saffron.system.domain.SocialUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author w
 * @since 2023/3/23
 */
@Service
public class SocialUserService {

    private final SocialUserDao socialUserDao;

    @Autowired
    public SocialUserService(SocialUserDao socialUserDao) {
        this.socialUserDao = socialUserDao;
    }

    public SocialUser findByUuidAndSource(String uuid, String source) {
        return socialUserDao.findByUuidAndSource(uuid,source);
    }

    public SocialUser save(SocialUser socialUser) {
        return socialUserDao.save(socialUser);
    }
}
