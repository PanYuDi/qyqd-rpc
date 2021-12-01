package github.qyqd.rpc.remote;

import github.qyqd.rpc.remote.message.ProtocolMessage;
import github.qyqd.rpc.remote.transport.netty.request.ProtocolRequest;
import github.qyqd.rpc.remote.transport.netty.request.ProtocolRequestEndpointWrapper;

/**
 * @Author: PanYuDi
 * @Date: 2021/11/8 21:05
 * @Description: 客户端Rpc接口
 */
public interface RpcClient {
    /**
     * 传输信息
     * @param message
     */
    public void send(ProtocolRequestEndpointWrapper message);
}
