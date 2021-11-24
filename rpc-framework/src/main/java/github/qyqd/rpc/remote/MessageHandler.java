package github.qyqd.rpc.remote;

/**
 * @Author: PanYuDi
 * @Date: 2021/11/8 20:43
 * @Description: TODO
 */
public interface MessageHandler<T extends RequestMessage> {
    public void handle(T message);
}
