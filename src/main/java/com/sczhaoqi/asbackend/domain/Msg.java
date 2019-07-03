package com.sczhaoqi.asbackend.domain;

import lombok.Data;

import static com.sczhaoqi.asbackend.domain.Msg.Status.FAILED;
import static com.sczhaoqi.asbackend.domain.Msg.Status.OK;

/**
 * @author sczhaoqi
 * @date 2019/6/27 21:50
 */
@Data
public class Msg<T>
{
    public enum Status
    {
        OK(1, "OK"),
        WARN(2, "WARN"),
        FAILED(3, "FAILED"),
        ERROR(4, "ERROR");

        private Integer code;
        private String msg;

        Status(int code, String msg)
        {
            this.code = code;
            this.msg = msg;
        }
    }

    private Integer code;
    private T data;
    private String msg;

    public Msg()
    {
    }

    public Msg(Integer code, String msg)
    {
        this.code = code;
        this.msg = msg;
    }

    public Msg(Integer code, T data, String msg)
    {
        this.code = code;
        this.data = data;
        this.msg = msg;
    }

    public static Msg ok()
    {
        return new Msg(OK.code, OK.msg);
    }

    public static Msg ok(String msg)
    {
        return new Msg(OK.code, msg);
    }

    public static <E> Msg<E> ok(E data, String msg)
    {
        return new Msg<E>(OK.code, data, msg);
    }

    public static Msg fail(String msg)
    {
        return new Msg(FAILED.code, msg);
    }
}
