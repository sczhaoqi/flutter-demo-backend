package com.sczhaoqi.asbackend.service.impl;

import com.sczhaoqi.asbackend.domain.User;
import com.sczhaoqi.asbackend.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * @author sczhaoqi
 * @date 2019/7/5 0:20
 */
@Slf4j
@Service
public class FileServiceImpl
        implements FileService
{
    @Value("${user.avatar.basePath}")
    private String avatarBasePath;

    @Override
    public String saveAvatar(User user, MultipartFile file)
    {
        try {
            String fileName = file.getOriginalFilename();
            File dest = new File(avatarBasePath + "/" + user.getId() + "/" + fileName);
            File parentPath = new File(dest.getParent());
            parentPath.mkdirs();

            dest.deleteOnExit();
            dest.createNewFile();

            file.transferTo(dest);
            return dest.getAbsolutePath();
        }
        catch (IOException ioe) {
            log.error("保存文件失败", ioe);
        }
        return null;
    }

    @Override
    public void afterPropertiesSet()
            throws Exception
    {
        if (avatarBasePath != null && !avatarBasePath.isEmpty()) {
            File file = FileUtils.getFile(avatarBasePath);
            if (!file.exists()) {
                file.mkdirs();
            }
        }
    }

    @Override
    public FileInputStream getAvatar(Long id, String fileName)
    {
        FileInputStream is = null;
        File filePic = new File(avatarBasePath + "/" + id + "/" + fileName);
        try {
            is = new FileInputStream(filePic);
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return is;
    }
}
