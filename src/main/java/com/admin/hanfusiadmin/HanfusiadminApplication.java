package com.admin.hanfusiadmin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class HanfusiadminApplication {

    public static void main(String[] args) {
        SpringApplication.run(HanfusiadminApplication.class, args);
    }

}

