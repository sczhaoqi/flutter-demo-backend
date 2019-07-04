package com.sczhaoqi.asbackend.dao.mapper;

import com.sczhaoqi.asbackend.domain.User;
import org.apache.ibatis.annotations.Param;

/**
 * @author sczhaoqi
 * @date 2019/6/23 23:29
 */
public interface UserMapper
{
    User findByUsername(@Param("userName") String userName);

    User findUserDetailsByUsername(@Param("userName") String userName);

    void updateAvatar(@Param("id") Long id, @Param("accessUri") String accessUri, @Param("fileName")  String fileName);

    User findById(@Param("id")Long id);
}
