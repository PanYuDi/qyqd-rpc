package github.qyqd.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @ClassName ResponseType
 * @Description 返回结果类型
 * @Author 潘语笛
 * @Date 24/12/2021 下午2:01
 * Version 1.0
 */
@AllArgsConstructor
@Getter
public enum ResponseType {
    SUCCESS(500, "成功"),
    RPC_FAILED(400, "RPC失败"),
    BIZ_FAILED(401, "业务失败"),
    FAILED(402, "其他错误")
    ;
    Integer code;
    String msg;
    public static ResponseType getEnum(Integer code) {
        for(ResponseType responseType:ResponseType.values()) {
            if(responseType.getCode().equals(code)) {
                return responseType;
            }
        }
        return null;
    }
    public static boolean success(Integer code) {
        return code.equals(500);
    }
}
