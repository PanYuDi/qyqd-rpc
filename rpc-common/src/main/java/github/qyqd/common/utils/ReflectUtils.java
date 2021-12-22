package github.qyqd.common.utils;

/**
 * @ClassName ReflectUtils
 * @Description 反射相关的工具类
 * @Author 潘语笛
 * @Date 22/12/2021 上午11:27
 * Version 1.0
 */
public class ReflectUtils {
    public static boolean isImplementation(Class<?> impl, Class<?> inter) {
        Class<?>[] interfaces = impl.getInterfaces();
        for(Class clazz:interfaces) {
            if(clazz == inter) {
                return true;
            }
        }
        return false;
    }
}
