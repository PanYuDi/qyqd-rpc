package github.qyqd.remote.transport.netty.channel;

import github.qyqd.common.exception.ProtocolException;
import github.qyqd.remote.transport.netty.MessageHandler;
import github.qyqd.remote.transport.netty.client.UnprocessedRequest;
import github.qyqd.remote.message.ProtocolMessage;
import github.qyqd.remote.transport.netty.handler.ResponseDeserilizeHandler;
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
    UnprocessedRequest unprocessedRequest = UnprocessedRequest.getSingleton();
    MessageHandler handler = new ResponseDeserilizeHandler();
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        log.debug("client read {}", msg);
        if(!(msg instanceof ProtocolMessage)) {
            throw new ProtocolException("client read failed");
        }
        handler.handle((ProtocolMessage)msg, ctx);
    }
}
