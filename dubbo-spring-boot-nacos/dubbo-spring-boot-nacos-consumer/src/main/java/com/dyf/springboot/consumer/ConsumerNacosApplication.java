package com.dyf.springboot.consumer;

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
public class ConsumerNacosApplication {
    public static void main(String[] args) {
        SpringApplication.run(ConsumerNacosApplication.class, args);
    }
}
