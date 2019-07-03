package com.sczhaoqi.asbackend.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletResponse;

import java.io.PrintWriter;

/**
 * @author sczhaoqi
 * @date 2019/5/4 14:46
 */
@Slf4j
public class ResponseUtil
{
    public static void write(HttpServletResponse response, Object o)
    {
        try {
            response.setContentType("application/json; charset=utf-8");
            PrintWriter out = response.getWriter();
            //json返回
            out.println(JSON.toJSONString(o, SerializerFeature.WriteMapNullValue));
            out.flush();
            out.close();
        }
        catch (Exception e) {
            log.error("e={}", e);
        }
    }
}
