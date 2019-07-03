package com.sczhaoqi.asbackend.exception;

/**
 * @author sczhaoqi
 * @date 2019/6/28 0:33
 */
public class CurrentNotSupportException
        extends RuntimeException
{
    public CurrentNotSupportException()
    {
        super("Not Support Yet");
    }
}
