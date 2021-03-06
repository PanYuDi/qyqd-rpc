package github.qyqd.remote.transport.netty.codec;

import github.qyqd.common.exception.MessageCodecException;
import github.qyqd.remote.message.ProtocolMessage;
import github.qyqd.remote.constant.ProtocolConstant;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @Author: PanYuDi
 * @Date: 2021/11/9 20:28
 * @Description: 消息编码器
 */
public class ChannelMessageEncoder extends MessageToByteEncoder<ProtocolMessage> {

    @Override
    protected void encode(ChannelHandlerContext ctx, ProtocolMessage msg, ByteBuf out) throws Exception {
        out.writeBytes(ProtocolConstant.MAGIC);
        if(msg.getContent().length >= ProtocolConstant.MAX_FRAME_LENGTH) {
            throw new MessageCodecException("message size exceed");
        }
        out.writeInt(msg.getContent().length + 14);
        out.writeByte(ProtocolConstant.CURRENT_VERSION);
        out.writeByte(msg.getMessageType().getCode());
        out.writeInt(msg.getRequestId());
        out.writeBytes(msg.getContent());
    }
}
