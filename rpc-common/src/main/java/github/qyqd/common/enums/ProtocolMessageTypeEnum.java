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
    TEST_MESSAGE(1, "测试消息"),
    RPC_MESSAGE(2, "rpc通信消息"),
    HEARTBEAT_REQUEST_MESSAGE(3, "心跳检测消息"),
    HEARTBEAT_RESPONSE_MESSAGE(4, "心跳检测返回消息")
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
