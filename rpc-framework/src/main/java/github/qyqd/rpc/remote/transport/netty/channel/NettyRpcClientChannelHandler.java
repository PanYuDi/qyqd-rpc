package github.qyqd.rpc.remote.transport.netty.channel;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @ClassName NettyRpcClientChannelHandler
 * @Description 处理服务端返回消息
 * @Author 潘语笛
 * @Date 29/11/2021 下午2:04
 * Version 1.0
 */
public class NettyRpcClientChannelHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println(msg);
    }
}
