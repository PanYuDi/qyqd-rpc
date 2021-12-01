package github.qyqd.rpc.remote.transport.serialize;

/**
 * @ClassName Serializer
 * @Description 序列化器接口
 * @Author 潘语笛
 * @Date 1/12/2021 下午3:19
 * Version 1.0
 */
public interface Serializer {
    /**
     * 序列化对象
     * @param obj
     * @return
     */
    byte[] serialize(Object obj);

    /**
     * 反序列化对象，传入实际的类数据和Class对象
     * @param data
     * @param clazz
     * @param <T>
     * @return
     */
    <T> T deSerialize(byte[] data, Class<T> clazz);

}
