package com.dyf.springboot.consumer;

import com.alibaba.fastjson2.JSON;
import org.apache.dubbo.common.config.ReferenceCache;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.utils.SimpleReferenceCache;
import org.apache.dubbo.rpc.service.GenericService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * <p>
 * 调用方没有接口及模型类元，知道服务的接口的全限定类名和方法名的情况下，可以通过泛化调用调用对应接口。
 * 比如：实现一个通用的服务测试框架
 * </p>
 *
 * @author duyafei
 * @since 2024-01-07
 */
@Component
public class GenericTask implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        GenericTest();
    }


    private void GenericTest() {
        GenericService genericService = buildGenericService(
                "com.dyf.springboot.api.DemoService"
                , "group1"
                , "1.0");

        Object result = genericService.$invoke("sayHello", new String[]{"java.lang.String"}, new Object[]{"g1"});
        System.out.println("GenericTask Response: " + JSON.toJSONString(result));
    }

    private GenericService buildGenericService(String interfaceClass, String group, String version) {
        ReferenceConfig<GenericService> referenceConfig = new ReferenceConfig<>();
        referenceConfig.setInterface(interfaceClass);
        referenceConfig.setGroup(group);
        referenceConfig.setVersion(version);
        referenceConfig.setTimeout(30000);
        referenceConfig.setGeneric("true");
        ReferenceCache referenceCache = SimpleReferenceCache.getCache();
        try {
            return referenceCache.get(referenceConfig);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}
