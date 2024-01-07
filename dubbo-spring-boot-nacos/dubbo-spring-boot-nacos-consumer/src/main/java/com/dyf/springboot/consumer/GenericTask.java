package com.dyf.springboot.consumer;

import com.alibaba.fastjson2.JSON;
import com.dyf.springboot.api.ContextService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.rpc.RpcContext;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * <p>
 *
 * </p>
 *
 * @author duyafei
 * @since 2024-01-07
 */
@Component
public class ContextTask implements CommandLineRunner {


    @DubboReference
    private ContextService contextService;

    @Override
    public void run(String... args) throws Exception {
        contextTest();
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
