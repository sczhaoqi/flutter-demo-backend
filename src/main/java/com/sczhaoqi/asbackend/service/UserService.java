package com.sczhaoqi.asbackend.service;

import com.sczhaoqi.asbackend.domain.User;
import com.sczhaoqi.asbackend.domain.dto.LoginDto;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * @author sczhaoqi
 * @date 2019/6/23 23:46
 */
public interface UserService
        extends JwtTokenService
{
    String genToken(LoginDto loginDto);

    User loadUserByUsername(String userName)
            throws UsernameNotFoundException;

    User updatePassword(UserDetails userDetails, String newPassword);

    String updateAvatar(User user,String avatar,String fileName);

    User findById(Long id);
}
