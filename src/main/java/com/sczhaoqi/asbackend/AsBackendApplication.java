package com.sczhaoqi.asbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class AsBackendApplication
{

    public static void main(String[] args)
    {
        SpringApplication.run(AsBackendApplication.class, args);
    }
}
