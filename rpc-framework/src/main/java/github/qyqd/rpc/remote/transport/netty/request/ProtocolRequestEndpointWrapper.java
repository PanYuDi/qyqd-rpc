package github.qyqd.rpc.remote.transport.netty.request;

import github.qyqd.common.enums.ProtocolMessageTypeEnum;
import lombok.Data;

/**
 * @ClassName ProtocolRequestWrapper
 * @Description 包装了实际传输地址和请求类型的请求
 * @Author 潘语笛
 * @Date 29/11/2021 下午3:25
 * Version 1.0
 */
@Data
public class ProtocolRequestEndpointWrapper implements ProtocolRequest {
    int port;
    String host;
    ProtocolMessageTypeEnum messageTypeEnum;
    ProtocolRequest requestBody;
}
