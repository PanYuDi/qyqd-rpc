package github.qyqd.common.enums;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @ClassName ProtocolMessageTypeEnum
 * @Description
 * @Author 潘语笛
 * @Date 24/11/2021 下午3:50
 * Version 1.0
 */
@AllArgsConstructor
public enum ProtocolMessageTypeEnum {
    RPC_MESSAGE(1, "rpc通信消息"),
    CONNECT_MESSAGE(1, "检查连接消息")
    ;
    int code;
    String message;
    public static ProtocolMessageTypeEnum getEnum(int code) {
        for(ProtocolMessageTypeEnum protocolMessageTypeEnum: ProtocolMessageTypeEnum.values()) {
            if(protocolMessageTypeEnum.code == code) {
                return protocolMessageTypeEnum;
            }
        }
        return null;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
