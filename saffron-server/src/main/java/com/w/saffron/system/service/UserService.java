package com.w.saffron.system.service;


import com.w.saffron.common.PageParam;
import com.w.saffron.common.PageResult;
import com.w.saffron.exception.OprException;
import com.w.saffron.system.dao.UserDao;
import com.w.saffron.system.domain.User;
import com.w.saffron.system.dto.UserDto;
import io.github.linpeilie.Converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

/**
 * @author w
 * @since 2023/3/22
 */
@Service
public class UserService {

    private final UserDao userDao;

    private final Converter converter;

    @Autowired
    public UserService(UserDao userDao, Converter converter) {
        this.userDao = userDao;
        this.converter = converter;
    }


    public User findByUsername(String username) {
        return userDao.findByUsername(username).orElseThrow(()-> new OprException("can't find user"));
    }

    public User findById(Long id) {
        return userDao.findById(id).orElseThrow(()-> new OprException("can't find user"));
    }

    public PageResult<UserDto> findPage(Integer current, Integer pageSize, String searchText, UserDto userDto) {
        User user = converter.convert(userDto,User.class);
        if (user==null){
            user = new User();
        }
        return PageResult.of(userDao.findAll(Example.of(user), PageParam.of(current,pageSize)).map((item)-> converter.convert(item,
            UserDto.class)).toList());
    }

    public User save(User user) {
        return userDao.save(user);
    }

    public User findByEmail(String email) {
        return userDao.findByEmail(email).orElseThrow(()-> new OprException("can't find user"));
    }
}
