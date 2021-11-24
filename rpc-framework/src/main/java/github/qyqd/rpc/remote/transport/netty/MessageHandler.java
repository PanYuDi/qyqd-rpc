package github.qyqd.rpc.remote.transport.netty;

import github.qyqd.rpc.remote.RequestMessage;
import io.netty.channel.ChannelHandlerContext;

/**
 * @Author: PanYuDi
 * @Date: 2021/11/8 20:43
 * @Description: 抽象类处理消息
 */
public interface MessageHandler<T extends RequestMessage> {
    public Object handle(T message, ChannelHandlerContext ctx);

}
