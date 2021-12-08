package github.qyqd.rpc.remote.transport.netty.channel;

import github.qyqd.common.exception.ProtocolException;
import github.qyqd.rpc.remote.message.ProtocolMessage;
import github.qyqd.rpc.remote.transport.netty.MessageHandler;
import github.qyqd.rpc.remote.transport.netty.handler.MessageHandlerContext;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName NettyRpcClientChannelHandler
 * @Description 处理服务端返回消息
 * @Author 潘语笛
 * @Date 29/11/2021 下午2:04
 * Version 1.0
 */
@Slf4j
public class NettyRpcClientChannelHandler extends ChannelInboundHandlerAdapter {
    MessageHandler messageHandler;
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        log.debug("client read {}", msg);
        if(!(msg instanceof ProtocolMessage)) {
            throw new ProtocolException("client read failed");
        }


    }
}
