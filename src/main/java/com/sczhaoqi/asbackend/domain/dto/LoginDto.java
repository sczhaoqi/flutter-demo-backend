package com.sczhaoqi.asbackend.domain.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author sczhaoqi
 * @date 2019/5/4 20:12
 */
@Data
public class LoginDto
        implements Serializable
{
    private String username;
    private String password;
    private Boolean remember;
}
