package github.qyqd.remote.transport.netty.channel;

import github.qyqd.remote.RequestMessage;
import github.qyqd.remote.message.ProtocolMessage;
import github.qyqd.remote.transport.netty.MessageHandler;
import github.qyqd.remote.transport.netty.handler.factory.ServerMessageHandlerContextFactory;
import github.qyqd.remote.utils.ProtocolMessageUtils;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;


/**
 * @ClassName NettyRpcServerChannelHandler
 * @Description server端实际消息处理handler
 * @Author 潘语笛
 * @Date 24/11/2021 下午4:54
 * Version 1.0
 */
public class NettyRpcServerChannelHandler extends ChannelInboundHandlerAdapter {
    MessageHandler messageHandler = new ServerMessageHandlerContextFactory().create();
    public NettyRpcServerChannelHandler() {
        super();
    }
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if(msg instanceof ProtocolMessage) {
            ProtocolMessageUtils.setRequestId(((ProtocolMessage) msg).getRequestId());
            if(messageHandler.canHandle(msg)) {
                messageHandler.handle((RequestMessage) msg, ctx);
            }
        }
    }
}
