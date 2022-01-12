package github.qyqd.providers;

import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.listener.NamingEvent;
import github.qyqd.common.exception.RpcException;
import github.qyqd.config.NacosConfig;
import github.qyqd.registry.utils.NacosUtils;
import github.qyqd.rpc.invoker.Invocation;

/**
 * @ClassName AbstractObserver
 * @Description TODO
 * @Author 潘语笛
 * @Date 12/1/2022 下午4:44
 * Version 1.0
 */
public abstract class AbstractObserver implements Observer{
    @Override
    public void updateInvocation(Invocation invocation, UpdateStatusEnum status) {

    }

    @Override
    public void subscribe(Invocation invocation) {
    }

}
