package github.qyqd.providers;

import github.qyqd.common.extension.SPI;
import github.qyqd.rpc.invoker.Invocation;

import java.util.List;

/**
 * @Author: 潘语笛
 * @Date: 28/1/2022 上午9:38
 * @Description: 服务搜索
 */
@SPI
public interface Directory {
    public List<Invocation> searchInvocation(Invocation invocation);

    public void subscribe(Invocation invocation, DirectoryListener directoryListener);

    @FunctionalInterface
    public interface DirectoryListener {
        public void onEvent(List<Invocation> invocationList);
    }

    public String generateUrl(Invocation invocation);
}
