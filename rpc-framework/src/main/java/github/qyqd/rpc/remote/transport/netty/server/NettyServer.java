package github.qyqd.rpc.remote.transport.netty.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import github.qyqd.rpc.remote.transport.netty.MessageHandler;
import github.qyqd.rpc.remote.RpcServer;
import github.qyqd.rpc.remote.entity.EndPoint;
import github.qyqd.rpc.remote.transport.netty.channel.NettyChannelHandlerInitializer;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @Author: PanYuDi
 * @Date: 2021/11/8 21:05
 * @Description: 底层Netty启动Rpc服务
 */
@Component
@Slf4j
public class NettyServer implements RpcServer {
    List<MessageHandler> handlers = new CopyOnWriteArrayList<>();
    EndPoint endPoint;
    EventLoopGroup bossGroup;
    EventLoopGroup workerGroup;
    public NettyServer(EndPoint endPoint, MessageHandler... messageHandlers) {
        handlers.addAll(Arrays.asList(messageHandlers));
        this.endPoint = endPoint;
    }
    @Override
    public void start() {
        bossGroup = new NioEventLoopGroup();
        workerGroup = new NioEventLoopGroup();
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 128)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childHandler(new NettyChannelHandlerInitializer(handlers));
        try {
            ChannelFuture cf = bootstrap.bind(endPoint.getPort()).sync();
            cf.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            log.error("occur exception when start server:", e);
        } finally {
            log.info("netty server closed");
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
        log.info("rpc server started at port" + endPoint.getPort());


    }
}
