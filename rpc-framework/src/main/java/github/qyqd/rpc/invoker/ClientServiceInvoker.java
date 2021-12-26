package github.qyqd.rpc.invoker;

import github.qyqd.common.enums.ProtocolMessageTypeEnum;
import github.qyqd.common.exception.RpcException;
import github.qyqd.common.exception.TimeOutException;
import github.qyqd.remote.RpcClient;
import github.qyqd.remote.message.rpc.RpcRequest;
import github.qyqd.remote.message.rpc.RpcResponse;
import github.qyqd.remote.transport.netty.client.NettyClient;
import github.qyqd.remote.transport.netty.request.ProtocolRequestEndpointWrapper;
import github.qyqd.remote.utils.ProtocolMessageUtils;
import github.qyqd.rpc.result.Result;
import github.qyqd.rpc.result.RpcResult;
import github.qyqd.rpc.route.BaseRoute;
import github.qyqd.rpc.route.DirectRouteParser;
import github.qyqd.rpc.route.Parser;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @ClassName ClientServiceInvoker
 * @Description 通过client发送远程消息执行
 * @Author 潘语笛
 * @Date 23/12/2021 下午5:26
 * Version 1.0
 */
@Slf4j
public class ClientServiceInvoker extends AbstractInvoker{
    private static RpcClient client = new NettyClient(1000 * 1000 * 1000L);
    // TODO 路由解析器，赋值
    Parser parser = new DirectRouteParser();
    @Override
    public Result doInvoke(Invocation invocation) {
        BaseRoute route = parser.parse(invocation.getUrl());
        ProtocolRequestEndpointWrapper wrapper = new ProtocolRequestEndpointWrapper();
        Integer requestId = ProtocolMessageUtils.generateRequestId();
        RpcRequest rpcRequest = new RpcRequest();
        rpcRequest.setRequestId(requestId);
        rpcRequest.setInterfaceName(invocation.getInterfaceName());
        rpcRequest.setMethodName(invocation.getMethodName());
        rpcRequest.setServiceName(invocation.getServiceName());
        rpcRequest.setParameters(invocation.getParameters());
        rpcRequest.setParameterTypes(invocation.getParameterTypes());
        wrapper.setRequestId(requestId);
        wrapper.setHost(route.getHost());
        wrapper.setPort(route.getPort());
        wrapper.setRequestBody(rpcRequest);
        wrapper.setMessageTypeEnum(ProtocolMessageTypeEnum.RPC_REQUEST);
        CompletableFuture send = (CompletableFuture)client.send(wrapper);
        Result result = new RpcResult();
        try {
            RpcResponse res = (RpcResponse)send.get();
            result.setValue(res);
            result.setException(res.getT());
        } catch (InterruptedException e) {
            e.printStackTrace();
            throw new RpcException("get result failed");
        } catch (ExecutionException e) {
            if(e.getCause() instanceof TimeOutException) {
                throw (TimeOutException)e.getCause();
            } else {
                e.getCause().printStackTrace();
                throw new RpcException("get invoke result failed");
            }
        }
        return result;
    }
}
