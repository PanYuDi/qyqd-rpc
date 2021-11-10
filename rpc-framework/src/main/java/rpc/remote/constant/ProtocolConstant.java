package rpc.remote.constant;

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
}
