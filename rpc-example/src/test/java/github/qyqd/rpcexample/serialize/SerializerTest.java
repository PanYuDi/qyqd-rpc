package github.qyqd.rpcexample.serialize;

import github.qyqd.rpc.remote.transport.serialize.ProtostuffSerializer;
import github.qyqd.rpc.remote.transport.serialize.Serializer;
import lombok.Data;
import org.junit.jupiter.api.Test;

/**
 * @ClassName SerializerTest
 * @Description 测试序列化
 * @Author 潘语笛
 * @Date 1/12/2021 下午4:28
 * Version 1.0
 */
public class SerializerTest {
    Serializer serializer = new ProtostuffSerializer();
    @Test
    public void testSerialize() {
        TestPojo testPojo = new TestPojo();
        testPojo.setName("pyd");
        testPojo.setNum(1);
        byte[] data = serializer.serialize(testPojo);
        System.out.println(data);
        TestPojo testPojo1 = serializer.deSerialize(data, TestPojo.class);
        System.out.println(testPojo1);
    }
}
