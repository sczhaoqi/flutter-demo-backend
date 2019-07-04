package com.sczhaoqi.asbackend.controller;

import com.sczhaoqi.asbackend.domain.Msg;
import com.sczhaoqi.asbackend.domain.User;
import com.sczhaoqi.asbackend.domain.dto.LoginDto;
import com.sczhaoqi.asbackend.service.FileService;
import com.sczhaoqi.asbackend.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @author sczhaoqi
 * @date 2019/6/28 0:02
 */
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController
        extends BaseController
{
    @Autowired
    private UserService userService;
    @Autowired
    private FileService fileService;

    @GetMapping("")
    public Msg<User> userInfo()
    {
        User user = currentUser();
        if (user != null) {
            return Msg.ok(user, "Get user info success!");
        }
        return Msg.fail("Not auth,Please try after Login!");
    }

    @PostMapping("login")
    public Msg<String> login(@RequestBody LoginDto loginDto)
    {
        String token = userService.genToken(loginDto);
        return Msg.ok(token, "Login success!");
    }

    @PostMapping(value = "/upload", headers = "content-type=multipart/form-data")
    public Msg<String> uploadAvatar(MultipartFile file)
    {
        User user = currentUser();
        String avatar = fileService.saveAvatar(user, file);
        String uri = userService.updateAvatar(user, avatar,file.getOriginalFilename());
        if (avatar != null) {
            return Msg.ok(uri, "保存成功");
        }
        else {
            return Msg.fail("保存失败");
        }
    }

    @GetMapping("/avatar/{id}")
    public void avatar(@PathVariable Long id, HttpServletResponse response)
            throws IOException
    {
        User user = userService.findById(id);
        response.setContentType("image/"+user.getFileName().substring(user.getFileName().lastIndexOf(".")+1));
        FileInputStream is = fileService.getAvatar(id,user.getFileName());

        if (is != null) {
            int i = is.available(); // 得到文件大小
            byte data[] = new byte[i];
            is.read(data); // 读数据
            is.close();
            response.setContentType("image/jpeg"); // 设置返回的文件类型
            OutputStream toClient = response.getOutputStream(); // 得到向客户端输出二进制数据的对象
            toClient.write(data); // 输出数据
            toClient.close();
        }
    }
}
