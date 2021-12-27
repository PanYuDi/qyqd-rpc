package github.qyqd.rpcexample.protocol;

import github.qyqd.remote.RpcServer;
import github.qyqd.remote.entity.EndPoint;
import github.qyqd.remote.transport.netty.server.NettyServer;

/**
 * @ClassName NettyServerExample
 * @Description TODO
 * @Author 潘语笛
 * @Date 8/12/2021 下午5:23
 * Version 1.0
 */
public class NettyServerExample {
    public static void main(String[] args) throws InterruptedException {
        RpcServer rpcServer = new NettyServer(EndPoint.builder().host("127.0.0.1").port(8088).build());
        rpcServer.start();
    }
}
