package github.qyqd.remote.transport.netty.handler.factory;

import github.qyqd.remote.transport.netty.MessageHandler;

/**
 * @ClassName MessageHandlerFactory
 * @Description 制造处理器的工厂类
 * @Author 潘语笛
 * @Date 8/12/2021 上午11:08
 * Version 1.0
 */
public interface MessageHandlerFactory {
    /**
     * 创建消息处理器工厂接口
     * @return
     */
    MessageHandler create();
}
