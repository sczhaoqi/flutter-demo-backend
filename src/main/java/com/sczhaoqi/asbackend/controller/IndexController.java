package com.sczhaoqi.asbackend.controller;

import com.sczhaoqi.asbackend.domain.Msg;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.sczhaoqi.asbackend.domain.Msg.ok;

/**
 * @author sczhaoqi
 * @date 2019/6/27 21:47
 */
@RestController
public class IndexController
{
    @GetMapping("/")
    public Msg index()
    {
        return ok();
    }
}
