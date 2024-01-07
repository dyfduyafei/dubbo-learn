package com.dyf.springboot.provider;

import com.alibaba.fastjson2.JSON;
import com.dyf.springboot.api.ContextService;
import org.apache.dubbo.config.annotation.DubboService;
import org.apache.dubbo.rpc.RpcContext;

import java.util.Map;

/**
 * <p>
 * ServiceContext：在 Dubbo 内部使用，用于传递调用链路上的参数信息，如 invoker 对象等
 * ClientAttachment：在 Client 端使用，往 ClientAttachment 中写入的参数将被传递到 Server 端
 * ServerAttachment：在 Server 端使用，从 ServerAttachment 中读取的参数是从 Client 中传递过来的
 * ServerContext：在 Client 端和 Server 端使用，用于从 Server 端回传 Client 端使用，Server 端写入到 ServerContext 的参数在调用结束后可以在 Client 端的 ServerContext 获取到
 * </p>
 *
 * @author duyafei
 * @since 2024-01-08
 */
@DubboService
public class ContextServiceImpl implements ContextService {
    @Override
    public String invoke(String param) {
        //ServerAttachment接收客户端传递过来的参数
        Map<String, Object> serverAttachments = RpcContext.getServerAttachment().getObjectAttachments();
        System.out.println("ContextService serverAttachments:" + JSON.toJSONString(serverAttachments));
        //往客户端传递参数
        RpcContext.getServerContext().setAttachments(
                Map.of("serverKey1", "服务端参数1",
                "serverKey2", "服务端参数2"));
        StringBuilder s = new StringBuilder();
        s.append("ContextService param:").append(param);
        return s.toString();
    }
}
