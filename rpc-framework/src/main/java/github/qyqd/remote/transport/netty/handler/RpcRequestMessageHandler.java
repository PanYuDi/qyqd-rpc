package github.qyqd.remote.transport.netty.handler;

import github.qyqd.common.enums.ProtocolMessageTypeEnum;
import github.qyqd.providers.Provider;
import github.qyqd.providers.ServiceManagerImpl;
import github.qyqd.remote.RequestMessage;
import github.qyqd.remote.message.heartbeat.HeartbeatMessage;
import github.qyqd.remote.message.rpc.RpcRequest;
import github.qyqd.remote.message.rpc.RpcResponse;
import github.qyqd.remote.transport.netty.MessageHandler;
import github.qyqd.remote.utils.ProtocolMessageUtils;
import github.qyqd.rpc.invoker.Invocation;
import github.qyqd.rpc.invoker.RpcInvocation;
import github.qyqd.rpc.result.Result;
import io.netty.channel.ChannelHandlerContext;
import io.protostuff.Rpc;
import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName RpcRequestMessageHandler
 * @Description 处理Rpc请求消息
 * @Author 潘语笛
 * @Date 24/12/2021 下午1:44
 * Version 1.0
 */
@Slf4j
public class RpcRequestMessageHandler implements MessageHandler {
    Provider provider = ServiceManagerImpl.getSingleton();
    @Override
    public Object handle(RequestMessage message, ChannelHandlerContext ctx) {
        RpcRequest rpcRequest = (RpcRequest) message;
        log.debug("rpc message received {}", rpcRequest);
        Invocation invocation = RpcInvocation.builder()
                .serviceName(rpcRequest.getServiceName())
                .methodName(rpcRequest.getMethodName())
                .parameters(rpcRequest.getParameters())
                .parameterTypes(rpcRequest.getParameterTypes())
                .build();
        Result invokeRes = provider.getInvoker(invocation).invoke(invocation);
        RpcResponse rpcResponse;
        if(invokeRes.getException() != null) {
            rpcResponse = RpcResponse.buildFailedResult(invokeRes.getException(), rpcRequest.getRequestId());
        } else {
            rpcResponse = RpcResponse.buildSuccessResult(invokeRes.getValue(), rpcRequest.getRequestId());
        }
        ctx.writeAndFlush(ProtocolMessageUtils.buildProtocolMessage(rpcResponse, ProtocolMessageTypeEnum.RPC_RESPONSE));
        return null;
    }

    @Override
    public boolean canHandle(Object message) {
        return message instanceof RpcRequest;
    }

    @Override
    public Class getHandleableType() {
        return RpcRequest.class;
    }
}
