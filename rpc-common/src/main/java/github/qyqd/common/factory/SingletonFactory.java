package github.qyqd.common.factory;

import github.qyqd.common.exception.RpcException;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.C;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author: 潘语笛
 * @Date: 25/1/2022 下午1:51
 * @Description: 单例工厂
 */
@Slf4j
public class SingletonFactory {
    private static ConcurrentMap<String, Object> INSTANCE_CACHE = new ConcurrentHashMap<>();
    private static ConcurrentMap<String, Lock> LOCK_MAP = new ConcurrentHashMap<>();
    public static Object getInstance(Class clazz) {
        String clazzName = clazz.getName();
        LOCK_MAP.putIfAbsent(clazzName, new ReentrantLock());
        if(!INSTANCE_CACHE.containsKey(clazzName)) {
            Lock lock = LOCK_MAP.get(clazzName);
            try {
                lock.lock();
                if(!INSTANCE_CACHE.containsKey(clazzName)) {
                    INSTANCE_CACHE.put(clazzName, clazz.newInstance());
                }
            } catch (InstantiationException e) {
                log.info("singleton instantiation failed cause = {}", e);
                throw new RpcException("singleton instantiation failed cause = {}");
            } catch (IllegalAccessException e) {
                log.info("singleton instantiation failed cause = {}", e);
                throw new RpcException("singleton instantiation failed cause = {}");
            } finally {
                lock.unlock();
            }
        }
        return INSTANCE_CACHE.get(clazzName);
    }
}
