package github.qyqd.rpcexample.protocol;

import github.qyqd.common.enums.ProtocolMessageTypeEnum;
import github.qyqd.remote.RequestMessage;
import github.qyqd.remote.RpcClient;
import github.qyqd.remote.transport.netty.client.NettyClient;
import github.qyqd.remote.transport.netty.request.ProtocolRequestEndpointWrapper;
import github.qyqd.remote.utils.ProtocolMessageUtils;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @ClassName NettyClientExample
 * @Description 测试Netty客户端
 * @Author 潘语笛
 * @Date 8/12/2021 下午5:28
 * Version 1.0
 */
public class NettyClientExample {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        RpcClient rpcClient = new NettyClient(1000 * 1000 * 1000L);
        ProtocolRequestEndpointWrapper wrapper = new ProtocolRequestEndpointWrapper();
        wrapper.setHost("127.0.0.1");
        wrapper.setPort(8088);
        wrapper.setMessageTypeEnum(ProtocolMessageTypeEnum.HEARTBEAT_REQUEST_MESSAGE);
        wrapper.setRequestBody(ProtocolMessageUtils.createHeartBeatRequestMessage());
        wrapper.setRequestId(ProtocolMessageUtils.generateRequestId());
        CompletableFuture<RequestMessage> send = (CompletableFuture<RequestMessage>) rpcClient.send(wrapper);
        RequestMessage x = send.get();
        System.out.println(x);
    }
}
