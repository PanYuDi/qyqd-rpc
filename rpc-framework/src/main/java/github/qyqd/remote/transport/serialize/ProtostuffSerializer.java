package github.qyqd.remote.transport.serialize;

import io.protostuff.LinkedBuffer;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;
import io.protostuff.ProtostuffIOUtil;
/**
 * @ClassName ProtostuffSerializer
 * @Description protostuff序列化器
 * @Author 潘语笛
 * @Date 1/12/2021 下午4:04
 * Version 1.0
 */
public class ProtostuffSerializer implements Serializer{
    private static LinkedBuffer BUFFER = LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE);
    @Override
    public byte[] serialize(Object obj) {
        Class<?> clazz = obj.getClass();
        Schema schema = RuntimeSchema.getSchema(clazz);
        byte[] bytes;
        try {
            bytes = ProtostuffIOUtil.toByteArray(obj, schema, BUFFER);
        } finally {
            BUFFER.clear();
        }
        return bytes;
    }

    @Override
    public <T> T deSerialize(byte[] data, Class<T> clazz) {
        Schema<T> tSchema = RuntimeSchema.getSchema(clazz);
        T obj = tSchema.newMessage();
        ProtostuffIOUtil.mergeFrom(data, obj, tSchema);
        return obj;
    }
}
