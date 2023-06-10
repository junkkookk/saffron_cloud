package com.w.saffron.system.dao;

import com.w.saffron.system.domain.SocialUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author w
 * @since 2023/3/23
 */
@Repository
public interface SocialUserDao extends JpaRepository<SocialUser,Long> {

    SocialUser findByUuidAndSource(String uuid, String source);
}
