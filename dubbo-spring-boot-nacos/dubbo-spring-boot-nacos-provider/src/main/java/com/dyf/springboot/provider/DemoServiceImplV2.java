package com.dyf.springboot.provider;

import com.dyf.springboot.api.DemoService;
import org.apache.dubbo.config.annotation.DubboService;

@DubboService(group = "group2",version = "2.0")
public class DemoServiceImplV2 implements DemoService {

    @Override
    public String sayHello(String name) {
        return "ServiceV2: Hello " + name;
    }
}