package github.qyqd.remote.constant;

import java.nio.charset.StandardCharsets;

/**
 * @Author: PanYuDi
 * @Date: 2021/11/10 20:36
 * @Description: 协议有关常数
 */
public class ProtocolConstant {
    public static final int MAGIC_LEN = 4;
    public static final int VERSION_LEN = 1;
    public static final int TYPE_LEN = 1;
    public static final int REQUEST_ID_LEN = 4;
    public static final int MAX_FRAME_LENGTH = 8 * 1024 * 1024;
    public static final byte[] MAGIC = "YEAH".getBytes(StandardCharsets.UTF_8);
    public static final int CURRENT_VERSION = 1;
    public static final byte[] HEARTBEAT_REQUEST_PAYLOAD = "PING".getBytes(StandardCharsets.UTF_8);
    public static final byte[] HEARTBEAT_RESPONSE_PAYLOAD = "PONG".getBytes(StandardCharsets.UTF_8);
    public static final int READ_IDLE_TIME_SECOND = 5;
    public static final int WRITE_IDLE_TIME_SECOND = 5;
    public static final int ALL_IDLE_TIME_SECOND = 10;

}
