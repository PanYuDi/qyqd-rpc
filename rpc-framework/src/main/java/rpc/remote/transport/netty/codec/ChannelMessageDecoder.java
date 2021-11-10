package rpc.remote.transport.netty.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import rpc.remote.message.RpcProtocolMessage;

import java.util.List;

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
public class ChannelMessageDecoder extends LengthFieldBasedFrameDecoder {
    public ChannelMessageDecoder(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength) {
        super(maxFrameLength, lengthFieldOffset, lengthFieldLength);
    }
}
