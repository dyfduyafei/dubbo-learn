package com.dyf.springboot.consumer;

import com.alibaba.fastjson2.JSON;
import com.dyf.springboot.api.ContextService;
import com.dyf.springboot.api.DemoService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.rpc.RpcContext;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;
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

    @DubboReference
    private ContextService contextService;

    @Override
    public void run(String... args) throws Exception {

        contextTest();

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

    private void contextTest() {
        // 向服务端传递参数 setAttachment 设置的 KV 对，在完成下面一次远程调用会被清空，即多次远程调用要多次设置。
        // path, group, version, dubbo, token, timeout 几个 key 是保留字段，请使用其它值。
        RpcContext.getClientAttachment().setAttachments(
                Map.of("clientKey1", "客户端参数1",
                        "clientKey2", "客户端参数2")
        );
        String res = contextService.invoke("context1");
        // 接收传递回来的参数
        Map<String, Object> clientAttachment = RpcContext.getServerContext().getObjectAttachments();
        System.out.println("ContextTask clientAttachment:" + JSON.toJSONString(clientAttachment));
        System.out.println("ContextService Return : " + res);
    }
}
