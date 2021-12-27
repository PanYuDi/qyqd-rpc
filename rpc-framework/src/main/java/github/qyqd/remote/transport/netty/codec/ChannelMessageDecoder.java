package github.qyqd.remote.transport.netty.codec;

import github.qyqd.common.enums.ProtocolMessageTypeEnum;
import github.qyqd.common.exception.MessageCodecException;
import github.qyqd.remote.message.ProtocolMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import lombok.extern.slf4j.Slf4j;
import github.qyqd.remote.constant.ProtocolConstant;

/** 通信协议
 * +--------+--------+--------+--------+--------+--------+--------+--------+--------+--------+--------+--------+--------+--------+--------+--------+
 * | 4 BYTE  |        |        |      4B  |        |        |        |1 B version | 1B  type |4B requestid |
 * +--------------------------------------------+--------+-----------------+--------+--------+--------+--------+--------+--------+-----------------+
 * |  magic | version|  type  |           content lenth           |        |           content byte[]                                        |        |
 * +--------+-----------------------------------------------------------------------------------------+--------------------------------------------+
 * 4B 魔数
 * 4B 长度
 * 1B 版本号
 * 1B 消息类型
 * 4B 请求id
 */

/**
 * @Author: PanYuDi
 * @Date: 2021/11/9 20:28
 * @Description: 消息解码器
 */
@Slf4j
public class ChannelMessageDecoder extends LengthFieldBasedFrameDecoder {
    public ChannelMessageDecoder() {
        super(ProtocolConstant.MAX_FRAME_LENGTH, 4,4, -8, 0);

    }
    public ChannelMessageDecoder(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength) {
        super(maxFrameLength, lengthFieldOffset, lengthFieldLength);
    }

    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        ProtocolMessage protocolMessage = new ProtocolMessage();
        Object decoded = super.decode(ctx, in);
        if (decoded instanceof ByteBuf) {
            ByteBuf buf = (ByteBuf) decoded;
            return decodeFrame(buf);
        }
        return decoded;
    }

    private ProtocolMessage decodeFrame(ByteBuf buf) {
        ProtocolMessage protocolMessage = new ProtocolMessage();
        checkMagic(buf);
        protocolMessage.setLen(buf.readInt() - 14);
        checkVersion(buf);
        protocolMessage.setMessageType(ProtocolMessageTypeEnum.getEnum(buf.readByte()));
        protocolMessage.setRequestId(buf.readInt());
        byte[] content = new byte[protocolMessage.getLen()];
        buf.readBytes(content);
        protocolMessage.setContent(content);
        return protocolMessage;
    }
    private void checkMagic(ByteBuf buf) {
        byte[] magicData = new byte[ProtocolConstant.MAGIC_LEN];
        buf.readBytes(magicData);
        for(int i = 0; i < magicData.length; i++) {
            if(magicData[i] != ProtocolConstant.MAGIC[i]) {
                throw new MessageCodecException("magic check failed");
            }
        }
    }
    private void checkVersion(ByteBuf buf) {
        byte b = buf.readByte();
        Integer version = Integer.valueOf(b);
        if(!version.equals(ProtocolConstant.CURRENT_VERSION)) {
            throw new MessageCodecException("version check failed");
        }
    }
}
