package github.qyqd.registry;

import github.qyqd.common.utils.ConcurrentUtils;
import github.qyqd.scanner.ServiceInfo;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @ClassName AsyncRegistry
 * @Description 异步注册任务
 * @Author 潘语笛
 * @Date 29/12/2021 下午3:38
 * Version 1.0
 */
@Slf4j
public abstract class AsyncRegistry implements Registry {
    ThreadPoolExecutor executor = ConcurrentUtils.getThreadPoolExecutor();
    /**
     * 失败重试次数
     */
    Integer RETRY_TIMES = 10;
    SuccessCallback sc = null;
    FailCallBack fc = null;
    public void setSuccessCallback(SuccessCallback sc) {
        this.sc = sc;
    }
    public void setFailCallback(FailCallBack fc) {
        this.fc = fc;
    }
    @Override
    public void register(ServiceInfo serviceInfo) {
        executor.execute(() -> {
            try {
                doRegister(serviceInfo);
                if(sc != null) {
                    sc.onSuccess(serviceInfo);
                }
            } catch (Exception e) {
                log.debug("encounter exception while regist {}, registry is {}", serviceInfo, this.getClass().getName());
                fc.onFail(serviceInfo, e);
            }
        });
    }

    /**
     *  实际的注册执行方法
     * @param serviceInfo
     */
    public abstract void doRegister(ServiceInfo serviceInfo);
    @FunctionalInterface
    interface SuccessCallback {
        /**
         * 回调
         * @param serviceInfo
         */
        public void onSuccess(ServiceInfo serviceInfo);
    }
    @FunctionalInterface
    interface FailCallBack {
        /**
         * 失败回调
         * @param serviceInfo
         * @param e
         */
        public void onFail(ServiceInfo serviceInfo, Exception e);
    }
}
