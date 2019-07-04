package com.sczhaoqi.asbackend.controller;

import com.sczhaoqi.asbackend.domain.User;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author sczhaoqi
 * @date 2019/7/5 0:06
 */
public class BaseController
{
    public User currentUser()
    {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof User) {
            return (User) principal;
        }
        throw new AuthenticationCredentialsNotFoundException("获取用户信息失败");
    }
}
