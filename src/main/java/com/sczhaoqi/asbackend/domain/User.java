package com.sczhaoqi.asbackend.domain;

import lombok.Data;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author sczhaoqi
 * @date 2019/6/23 23:31
 */
@Data
public class User
{
    Long id;
    String avatar;
    String username;
    String password;
    String email;
    String phoneNum;
    Boolean enabled;
    List<String> authorities;
    private String fileName;

    public List<SimpleGrantedAuthority> getAuthorityInfo()
    {
        return authorities.parallelStream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }

    public String getFileName()
    {
        return fileName;
    }

    public void setFileName(String fileName)
    {
        this.fileName = fileName;
    }
}
