package github.qyqd.remote.transport.netty.channel;

import github.qyqd.common.enums.ProtocolMessageTypeEnum;
import github.qyqd.common.exception.ProtocolException;
import github.qyqd.remote.RequestMessage;
import github.qyqd.remote.message.heartbeat.HeartbeatMessage;
import github.qyqd.remote.transport.netty.MessageHandler;
import github.qyqd.remote.transport.netty.client.NettyChannelContext;
import github.qyqd.remote.transport.netty.client.UnprocessedRequest;
import github.qyqd.remote.message.ProtocolMessage;
import github.qyqd.remote.transport.netty.handler.HeartbeatResponseMessageHandler;
import github.qyqd.remote.transport.netty.handler.ResponseDeserilizeHandler;
import github.qyqd.remote.utils.ProtocolMessageUtils;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

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
    NettyChannelContext nettyChannelContext;
    public NettyRpcClientChannelHandler(NettyChannelContext nettyChannelContext) {
        this.nettyChannelContext = nettyChannelContext;
    }
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if(evt instanceof IdleStateEvent) {
            IdleState idleState = ((IdleStateEvent) evt).state();
            if(idleState == IdleState.WRITER_IDLE) {
                InetSocketAddress socketAddress = (InetSocketAddress) ctx.channel().remoteAddress();
                // 很久没接收到数据了，发一个心跳
                ProtocolMessageUtils.generateRequestId();
                HeartbeatMessage heartBeatRequestMessage = ProtocolMessageUtils.createHeartBeatRequestMessage();
                ProtocolMessage protocolMessage = ProtocolMessageUtils.buildProtocolMessage(heartBeatRequestMessage, ProtocolMessageTypeEnum.HEARTBEAT_REQUEST_MESSAGE);
                CompletableFuture<RequestMessage> resultFuture = new CompletableFuture<>();
                unprocessedRequest.putUnprocessedRequest(protocolMessage.getRequestId(), resultFuture);
                // 添加超时回调，关闭tcp连接并移除容器
                resultFuture.exceptionally(e -> {
                    // 发生心跳超时， 关闭对应连接
                    log.info("cannot receive heartbeat response from {} so the connection will be closed", socketAddress.toString());
                    Channel channel = nettyChannelContext.get(socketAddress);
                    channel.close();
                    nettyChannelContext.remove(socketAddress);
                    return null;
                });
                ctx.writeAndFlush(protocolMessage);
//                try {
//                    // 接收到返回消息，如果正常则不管了
//                    RequestMessage requestMessage = resultFuture.get();
//                    log.debug("receive heartbeat response from {} ", socketAddress.toString());
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                } catch (ExecutionException e) {
//                    // 发生心跳超时， 关闭对应连接
//                    log.info("cannot receive heartbeat response from {} so the connection will be closed", socketAddress.toString());
//                    Channel channel = nettyChannelContext.get(socketAddress);
//                    channel.close();
//                    nettyChannelContext.remove(socketAddress);
//                }
            }
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        log.debug("client read {}", msg);
        if(!(msg instanceof ProtocolMessage)) {
            throw new ProtocolException("client read failed");
        }
        handler.handle((ProtocolMessage)msg, ctx);
    }
}
