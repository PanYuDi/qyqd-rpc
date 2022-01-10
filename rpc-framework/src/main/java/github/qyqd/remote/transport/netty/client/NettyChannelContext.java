package github.qyqd.remote.transport.netty.client;

import io.netty.channel.Channel;

import java.net.InetSocketAddress;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ClassName NettyChannelContext
 * @Description 长连接的Channel缓存
 * @Author 潘语笛
 * @Date 10/1/2022 下午5:17
 * Version 1.0
 */
public class NettyChannelContext {
    /**
     * netty的Channel缓存
     */
    Map<String, Channel> channelMap = new ConcurrentHashMap<>();

    public void put(InetSocketAddress inetSocketAddress, Channel channel ) {
        String key = inetSocketAddress.toString();
        channelMap.put(key, channel);
    }

    public void remove(InetSocketAddress inetSocketAddress) {
        channelMap.remove(inetSocketAddress.toString());
    }
    public Channel get(InetSocketAddress inetSocketAddress) {
        Channel channel = channelMap.get(inetSocketAddress.toString());
        if(channel != null) {
            if(channel.isActive()) {
                return channel;
            } else {
                channelMap.remove(inetSocketAddress.toString());
            }
        }
        return null;
    }
}
