package com.dyf.springboot.provider;

import com.dyf.springboot.api.DemoService;
import org.apache.dubbo.config.annotation.DubboService;

@DubboService(group = "group1",version = "1.0")
public class DemoServiceImpl implements DemoService {

    @Override
    public String sayHello(String name) {
        return "Hello " + name;
    }
}