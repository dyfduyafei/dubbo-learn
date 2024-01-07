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

    @DubboReference(group = "group1", version = "1.0")
    private DemoService demoService;

    @DubboReference(group = "group2", version = "2.0")
    private DemoService demoServiceV2;


    @Override
    public void run(String... args) throws Exception {
        serviceInvokerTest();
    }

    private void serviceInvokerTest() {
        String result = demoService.sayHello("world");
        System.out.println("接收到结果====>" + result);
        new Thread(() -> {
            while (true) {
                try {
                    TimeUnit.SECONDS.sleep(1);
                    System.out.println(new Date() + "---Task1 接收到结果====>" + demoService.sayHello("world"));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Thread.currentThread().interrupt();
                }
            }
        }, "Task1").start();

        new Thread(() -> {
            while (true) {
                try {
                    TimeUnit.SECONDS.sleep(1);
                    System.out.println(new Date() + "---Task2 接收到结果====>" + demoServiceV2.sayHello("world"));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Thread.currentThread().interrupt();
                }
            }
        }, "Task2").start();
    }

}
