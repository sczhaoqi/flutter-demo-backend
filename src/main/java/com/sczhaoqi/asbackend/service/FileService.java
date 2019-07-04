package com.sczhaoqi.asbackend.service;

import com.sczhaoqi.asbackend.domain.User;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;

/**
 * @author sczhaoqi
 * @date 2019/7/5 0:18
 */
public interface FileService
        extends InitializingBean
{
    /**
     * 保存头像
     *
     * @param user
     * @param file
     * @return
     */
    String saveAvatar(User user, MultipartFile file);

    FileInputStream getAvatar(Long id,String fileName);
}
