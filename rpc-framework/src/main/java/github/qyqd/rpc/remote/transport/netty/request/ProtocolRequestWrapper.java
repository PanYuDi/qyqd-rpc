package github.qyqd.rpc.remote.transport.netty.request;

import github.qyqd.common.enums.ProtocolMessageTypeEnum;
import lombok.Data;

/**
 * @ClassName ProtocolRequestWrapper
 * @Description TODO
 * @Author 潘语笛
 * @Date 29/11/2021 下午3:25
 * Version 1.0
 */
@Data
public class ProtocolRequestWrapper implements ProtocolRequest {
    int ip;
    String host;
    ProtocolMessageTypeEnum messageTypeEnum;
    ProtocolRequest requestBody;
}
