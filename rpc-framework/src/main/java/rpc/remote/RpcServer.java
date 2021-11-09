package rpc.remote;

/**
 * @Author: PanYuDi
 * @Date: 2021/11/8 21:04
 * @Description: 服务端启动类
 */
public interface RpcServer {
    /**
     * 启动Rpc服务端
     */
    void start() throws InterruptedException;
}
