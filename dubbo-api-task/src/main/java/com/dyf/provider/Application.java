package com.dyf.provider;

import com.dyf.api.GreetingsService;
import org.apache.dubbo.config.ProtocolConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.ServiceConfig;
import org.apache.dubbo.config.bootstrap.DubboBootstrap;

/**
 * <p>
 *
 * </p>
 *
 * @author duyafei
 * @since 2024-01-07
 */
public class Application {
    public static void main(String[] args) {
        // 定义具体的服务
        ServiceConfig<GreetingsService> serviceConfig = new ServiceConfig<>();
        serviceConfig.setInterface(GreetingsService.class);
        serviceConfig.setRef(new GreetingsServiceImpl());

        // 启动dubbo
        DubboBootstrap.getInstance()
                .application("first-dubbo-provider")
                .registry(new RegistryConfig("zookeeper://127.0.0.1:2181"))
                .protocol(new ProtocolConfig("dubbo", -1))
                .service(serviceConfig)
                .start()
                .await();


    }
}
