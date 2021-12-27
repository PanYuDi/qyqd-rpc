package github.qyqd.remote.transport.netty.server;

import github.qyqd.config.RpcConfig;
import github.qyqd.remote.transport.netty.channel.NettyChannelHandlerInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import github.qyqd.remote.RpcServer;
import github.qyqd.remote.entity.EndPoint;

/**
 * @Author: PanYuDi
 * @Date: 2021/11/8 21:05
 * @Description: 底层Netty启动Rpc服务
 */
@Slf4j
public class NettyServer implements RpcServer {
    EventLoopGroup bossGroup;
    EventLoopGroup workerGroup;
    @Override
    public void start() {
        bossGroup = new NioEventLoopGroup();
        workerGroup = new NioEventLoopGroup();
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 128)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childHandler(new NettyChannelHandlerInitializer());
        try {
            ChannelFuture cf = bootstrap.bind(RpcConfig.PORT).sync();
            log.info("rpc server started at port " + RpcConfig.PORT);
            cf.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            log.error("occur exception when start server:", e);
        } finally {
            log.info("netty server closed");
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }


    }
}
