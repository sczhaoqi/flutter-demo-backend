package com.sczhaoqi.asbackend.service;

import com.sczhaoqi.asbackend.domain.User;

/**
 * @author sczhaoqi
 * @date 2019/6/28 0:28
 */
public interface JwtTokenService
{
    String createToken(User user, Boolean rememberMe);

    String refreshToken(String token);

    boolean verifyToken(String token);

    User parseToken(String token);
}
