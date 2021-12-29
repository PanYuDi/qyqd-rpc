package github.qyqd.registry;

import github.qyqd.scanner.ServiceInfo;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @ClassName AsyncRegistry
 * @Description 异步注册任务
 * @Author 潘语笛
 * @Date 29/12/2021 下午3:38
 * Version 1.0
 */
public abstract class AsyncRegistry implements Registry {
    @Override
    public void register(ServiceInfo serviceInfo) {

    }

    /**
     *
     * @param serviceInfo
     */
    public abstract void doRegister(ServiceInfo serviceInfo);
}
