package github.qyqd.remote.message;

import github.qyqd.common.enums.ProtocolMessageTypeEnum;
import github.qyqd.remote.RequestMessage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: PanYuDi
 * @Date: 2021/11/8 20:52
 * @Description: rpc基础协议消息
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProtocolMessage implements RequestMessage {
    /**
     * 总长度
     */
    int len;
    /**
     * 请求负载数据类型
     */
    ProtocolMessageTypeEnum messageType;
    /**
     * 用于判断是那个请求
     */
    int requestId;
    /**
     * 实际信息负载
     */
    byte[] content;
}
