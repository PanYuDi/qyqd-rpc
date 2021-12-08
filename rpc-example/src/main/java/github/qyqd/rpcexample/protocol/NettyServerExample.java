package github.qyqd.rpcexample.protocol;

import github.qyqd.rpc.remote.RpcServer;
import github.qyqd.rpc.remote.entity.EndPoint;
import github.qyqd.rpc.remote.transport.netty.server.NettyServer;

/**
 * @ClassName NettyServerExample
 * @Description TODO
 * @Author 潘语笛
 * @Date 8/12/2021 下午5:23
 * Version 1.0
 */
public class NettyServerExample {
    public static void main(String[] args) throws InterruptedException {
        RpcServer rpcServer = new NettyServer(EndPoint.builder().host("127.0.0.1").port(8080).build());
        rpcServer.start();
    }
}
