package com.sczhaoqi.asbackend.dao.mapper2;

import com.sczhaoqi.asbackend.domain.User;
import org.apache.ibatis.annotations.Param;

/**
 * @author sczhaoqi
 * @date 2019/6/24 0:16
 */
public interface UserMapper2
{
    User findByPhoneNum(@Param("phoneNum") String phoneNum);
}
