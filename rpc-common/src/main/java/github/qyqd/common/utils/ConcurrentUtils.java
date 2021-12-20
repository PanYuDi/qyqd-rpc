package github.qyqd.common.utils;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName ConcurrentUtils
 * @Description 多线程工具类
 * @Author 潘语笛
 * @Date 20/12/2021 下午3:02
 * Version 1.0
 */
public class ConcurrentUtils {
    private static ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
            Runtime.getRuntime().availableProcessors(),
            Runtime.getRuntime().availableProcessors() * 2,
            30,
            TimeUnit.MILLISECONDS,
            new LinkedBlockingDeque<Runnable>(), new ThreadFactoryBuilder().setNameFormat("qyqd-rpc-thread-pool-%d").build());
    public static ThreadPoolExecutor getThreadPoolExecutor() {
        return threadPoolExecutor;
    }
}
