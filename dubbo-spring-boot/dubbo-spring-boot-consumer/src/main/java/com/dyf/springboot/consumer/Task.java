package com.dyf.springboot.consumer;

import com.dyf.springboot.api.DemoService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 *
 * </p>
 *
 * @author duyafei
 * @since 2024-01-07
 */
@Component
public class Task implements CommandLineRunner {

    @DubboReference
    private DemoService demoService;
    @Override
    public void run(String... args) throws Exception {
        String result = demoService.sayHello("world");
        System.out.println("接收到结果====>" + result);
        new  Thread(()->{
            while (true) {
                try {
                    TimeUnit.SECONDS.sleep(1);
                    System.out.println(new Date()+" 接收到结果====>" + demoService.sayHello("world"));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Thread.currentThread().interrupt();
                }
            }
        }).start();
    }
}
