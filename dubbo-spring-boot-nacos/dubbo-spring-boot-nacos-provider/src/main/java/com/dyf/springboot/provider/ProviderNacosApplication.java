package com.dyf.springboot.provider;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * <p>
 *
 * </p>
 *
 * @author duyafei
 * @since 2024-01-07
 */
@SpringBootApplication
@EnableDubbo
public class ProviderNacosApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProviderNacosApplication.class, args);
    }
}
