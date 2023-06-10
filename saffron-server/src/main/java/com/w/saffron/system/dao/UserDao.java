package com.w.saffron.system.dao;

import com.w.saffron.system.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author w
 * @since 2023/3/22
 */
@Repository
public interface UserDao extends JpaRepository<User,Long> {

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);
}
