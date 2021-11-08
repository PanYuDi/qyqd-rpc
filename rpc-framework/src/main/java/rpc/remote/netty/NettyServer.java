package rpc.remote.netty;

import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.util.concurrent.DefaultEventExecutorGroup;
import org.springframework.stereotype.Component;
import rpc.remote.MessageHandler;
import rpc.remote.RpcServer;
import sun.rmi.runtime.RuntimeUtil;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @Author: PanYuDi
 * @Date: 2021/11/8 21:05
 * @Description: 底层Netty启动Rpc服务
 */
@Component
public class NettyServer implements RpcServer {
    List<MessageHandler> handlers = new CopyOnWriteArrayList<>();
    @Override
    public void start() {
    }
}
