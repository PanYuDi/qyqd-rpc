package rpc.remote.message;

import rpc.remote.RequestMessage;

/**
 * @Author: PanYuDi
 * @Date: 2021/11/8 20:52
 * @Description: rpc基础协议消息
 */
public class RpcProtocolMessage implements RequestMessage {
    /**
     * 总长度
     */
    int len;
    /**
     * rpc协议版本号
     */
    String version;
    /**
     * 请求负载数据类型
     */
    String messageType;
    /**
     * 用于判断是那个请求
     */
    int requestId;
    /**
     * 实际信息负载
     */
    byte[] content;
}
