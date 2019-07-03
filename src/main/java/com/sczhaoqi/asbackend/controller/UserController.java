package com.sczhaoqi.asbackend.controller;

import com.sczhaoqi.asbackend.domain.Msg;
import com.sczhaoqi.asbackend.domain.User;
import com.sczhaoqi.asbackend.domain.UserAccount;
import com.sczhaoqi.asbackend.domain.dto.LoginDto;
import com.sczhaoqi.asbackend.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Security;

/**
 * @author sczhaoqi
 * @date 2019/6/28 0:02
 */
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController
{
    @Autowired
    private UserService userService;
    @GetMapping("")
    public Msg<User> currentUser(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal instanceof User){
            return Msg.ok((User) principal,"Get user info success!");
        }
        return Msg.fail("Not auth,Please try after Login!");
    }
    @PostMapping("login")
    public Msg<String> login(@RequestBody LoginDto loginDto){
        String token = userService.genToken(loginDto);
        return Msg.ok(token,"Login success!");
    }
}
