package github.qyqd.remote;

import github.qyqd.remote.transport.netty.request.ProtocolRequestEndpointWrapper;

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
    public Object send(ProtocolRequestEndpointWrapper message);
}
