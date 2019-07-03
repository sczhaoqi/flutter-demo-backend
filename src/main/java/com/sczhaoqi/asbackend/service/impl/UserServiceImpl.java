package com.sczhaoqi.asbackend.service.impl;

import com.sczhaoqi.asbackend.dao.mapper.UserMapper;
import com.sczhaoqi.asbackend.domain.User;
import com.sczhaoqi.asbackend.domain.dto.LoginDto;
import com.sczhaoqi.asbackend.exception.CurrentNotSupportException;
import com.sczhaoqi.asbackend.service.UserService;
import com.sczhaoqi.asbackend.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * @author sczhaoqi
 * @date 2019/6/23 23:48
 */
@Service
public class UserServiceImpl
        implements UserService
{
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User loadUserByUsername(String s)
            throws UsernameNotFoundException
    {
        if (StringUtils.isEmpty(s)) {
            throw new UsernameNotFoundException("Username may not be empty or null");
        }
        User user = userMapper.findUserDetailsByUsername(s);
        if (user == null) {
            throw new UsernameNotFoundException("User Not Found");
        }
        return user;
    }

    @Override
    public User updatePassword(UserDetails userDetails, String newPassword)
    {
        throw new CurrentNotSupportException();
    }

    @Override
    public String genToken(LoginDto loginDto)
    {
        User user = loadUserByUsername(loginDto.getUsername());
        if (!passwordEncoder.matches(loginDto.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("Invalid Username|Password!");
        }
        return createToken(user, loginDto.getRemember() != null ? loginDto.getRemember() : false);
    }

    @Override
    public String createToken(User user, Boolean rememberMe)
    {
        return jwtTokenUtil.createToken(user, rememberMe);
    }

    @Override
    public String refreshToken(String token)
    {
        throw new CurrentNotSupportException();
    }

    @Override
    public boolean verifyToken(String token)
    {
        return jwtTokenUtil.verifyToken(token);
    }

    @Override
    public User parseToken(String token)
    {
        return jwtTokenUtil.getUser(token);
    }
}
