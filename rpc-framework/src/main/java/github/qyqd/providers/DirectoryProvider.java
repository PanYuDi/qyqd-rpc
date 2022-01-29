package github.qyqd.providers;

import github.qyqd.common.extension.ExtensionLoader;
import github.qyqd.config.ExtensionConfig;
import github.qyqd.rpc.invoker.Invocation;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @ClassName NacosProvider
 * @Description Nacos执行器,主要任务是将nacos路由转换为direct路由
 * @Author 潘语笛
 * @Date 31/12/2021 上午9:29
 * Version 1.0
 */
@Slf4j
public class DirectoryProvider extends AbstractCachedProvider {
    Directory directory = ExtensionLoader.getExtensionLoader(Directory.class).getExtension(ExtensionConfig.directory);
    @Override
    public List<Invocation> getInvocation(Invocation invocation) {
        return directory.searchInvocation(invocation);
    }

    /**
     * 监听nacos-server，更新
     * @param invocation
     */
    @Override
    public void subscribe(Invocation invocation) {
        if(!invocationDirectory.containsKey(invocation.getServiceName())) {
            directory.subscribe(invocation, (invocationList) -> {
                updateInvocation(invocation.getServiceName(), invocationList, UpdateStatusEnum.UPDATE);
            });
        }
    }
}
