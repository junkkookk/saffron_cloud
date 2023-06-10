package com.w.saffron.system.dao;

import com.w.saffron.application.sys.domain.SocialUserRel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author w
 * @since 2023/3/23
 */
@Repository
public interface SocialUserRelDao extends JpaRepository<SocialUserRel,Long> {
    SocialUserRel findBySocialUserId(Long id);
}
