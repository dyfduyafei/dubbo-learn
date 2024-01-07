package com.dyf.client;

import com.dyf.api.GreetingsService;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.bootstrap.DubboBootstrap;

import java.io.IOException;

/**
 * <p>
 *
 * </p>
 *
 * @author duyafei
 * @since 2024-01-07
 */
public class Application {
    public static void main(String[] args) throws IOException {
        // 定义订阅的服务信息,包括接口
        ReferenceConfig<GreetingsService> referenceConfig = new ReferenceConfig<>();
        referenceConfig.setInterface(GreetingsService.class);

        // 配置 Dubbo 启动器，传入了应用名，注册中心地址，协议的信息以及服务的信息
        // 注：DubboBootstrap 中支持 service 和 reference 可以同时传入，
        // 意味着一个应用可以同时即是消费端、也是服务端。
        DubboBootstrap.getInstance()
                .application("first-dubbo-consumer")
                .registry(new RegistryConfig("zookeeper://127.0.0.1:2181"))
                .reference(referenceConfig);

        GreetingsService service = referenceConfig.get();
        String message = service.sayHi("dubbo");
        System.out.println("接收到结果: ==>" + message);
        // 挂起等待(防止进程退出）
        System.in.read();
    }
}
