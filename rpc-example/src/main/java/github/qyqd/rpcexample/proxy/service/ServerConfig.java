package github.qyqd.rpcexample.proxy.service;

import github.qyqd.remote.RpcServer;
import github.qyqd.remote.entity.EndPoint;
import github.qyqd.remote.transport.netty.server.NettyServer;

/**
 * @Author: 潘语笛
 * @Date: 26/12/2021 下午3:37
 * @Description: TODO
 */
public class ServerConfig {
    public static void main(String[] args) throws InterruptedException {
        RpcServer rpcServer = new NettyServer(EndPoint.builder().host("127.0.0.1").port(8088).build());
        rpcServer.start();
    }
}
