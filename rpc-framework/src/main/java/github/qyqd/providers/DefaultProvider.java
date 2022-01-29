package github.qyqd.providers;

import github.qyqd.common.extension.ExtensionLoader;
import github.qyqd.config.ExtensionConfig;
import github.qyqd.rpc.invoker.Invocation;
import github.qyqd.rpc.invoker.Invoker;
import github.qyqd.rpc.invoker.ProxyInvoker;

/**
 * @ClassName DefaultProvider
 * @Description 默认提供者,适用于不提供url的情况
 * @Author 潘语笛
 * @Date 31/12/2021 下午3:41
 * Version 1.0
 */
public class DefaultProvider implements Provider {
    Directory directory = ExtensionLoader.getExtensionLoader(Directory.class).getExtension(ExtensionConfig.directory);
    @Override
    public Invoker getInvoker(Invocation invocation) {
        if(invocation.getUrl().isEmpty() || invocation.getUrl() == null) {
            invocation.setUrl(directory.generateUrl(invocation));
        }
        return new ProxyInvoker();
    }
}
