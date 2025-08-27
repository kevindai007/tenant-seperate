package com.kevindai.base.tenantseparate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class TenantSeparateApplication {

    public static void main(String[] args) {
        SpringApplication.run(TenantSeparateApplication.class, args);
    }

}
