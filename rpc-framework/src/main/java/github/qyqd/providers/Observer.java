package github.qyqd.providers;

import github.qyqd.rpc.invoker.Invocation;
import lombok.AllArgsConstructor;

import java.util.List;

/**
 * @ClassName Observer
 * @Description 远程服务监听观察者
 * @Author 潘语笛
 * @Date 12/1/2022 下午3:05
 * Version 1.0
 */
public interface Observer {
    /**
     * 更新Invocation
     * @param serviceName
     * @param invocationList
     * @param status
     */
    public void updateInvocation(String serviceName, List<Invocation> invocationList, UpdateStatusEnum status);

    /**
     * 监听注册中心
     * @param invocation
     */
    public void subscribe(Invocation invocation);
    @AllArgsConstructor
    enum UpdateStatusEnum {
        ADD,
        UPDATE,
        REMOVE
        ;
    }
}
