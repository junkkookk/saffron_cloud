package com.w.saffron.covert;

import com.w.saffron.system.domain.User;
import com.w.saffron.system.dto.ProfileDto;
import com.w.saffron.system.vo.ProfileVo;

/**
 * @author w
 * @since 2023/6/15
 */
public class UserConverter {

    public static User toUser(ProfileDto profileDto){

        if (profileDto == null) {
            return null;
        }
        User user = new User();
        user.setUsername(profileDto.getUsername());
        user.setEmail(profileDto.getEmail());
        user.setAvatar(profileDto.getAvatar());
        user.setGender(profileDto.getGender());
        user.setBirthday(profileDto.getBirthday());
        user.setLastTime(profileDto.getLastTime());
        user.setId(profileDto.getId());
        return user;
    };

    public static ProfileVo toUserProfileVo(User user) {
        if (user == null) {
            return null;
        }
        ProfileVo profileVo = new ProfileVo();
        profileVo.setId(user.getId());
        profileVo.setUsername(user.getUsername());
        profileVo.setEmail(user.getEmail());
        profileVo.setAvatar(user.getAvatar());
        profileVo.setGender(user.getGender());
        profileVo.setLastTime(user.getLastTime());
        return profileVo;
    }

}
