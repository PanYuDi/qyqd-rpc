package github.qyqd.rpcexample.protocol;

import github.qyqd.remote.RpcServer;
import github.qyqd.remote.entity.EndPoint;
import github.qyqd.remote.transport.netty.server.NettyServer;
import org.junit.jupiter.api.Test;

/**
 * @ClassName NettyServerTest
 * @Description 测试rpc服务端的正常启动
 * @Author 潘语笛
 * @Date 8/12/2021 下午5:02
 * Version 1.0
 */
public class NettyServerTest {
    @Test
    public void startServer() throws InterruptedException {
        RpcServer rpcServer = new NettyServer(EndPoint.builder().host("127.0.0.1").port(8080).build());
        rpcServer.start();
    }
}
