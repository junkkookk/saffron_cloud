package com.w.saffron.system.service;


import com.w.saffron.application.sys.domain.SocialUserRel;
import com.w.saffron.system.dao.SocialUserRelDao;
import org.springframework.stereotype.Service;

/**
 * @author w
 * @since 2023/3/23
 */
@Service
public class SocialUserRelService {

    private final SocialUserRelDao socialUserRelDao;

    public SocialUserRelService(SocialUserRelDao socialUserRelDao) {
        this.socialUserRelDao = socialUserRelDao;
    }

    public SocialUserRel findBySocialUserId(Long id) {
        return socialUserRelDao.findBySocialUserId(id);
    }

    public void save(SocialUserRel socialUserRel) {
         socialUserRelDao.save(socialUserRel);
    }
}
