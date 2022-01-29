package github.qyqd.providers.loadbalance;

import github.qyqd.rpc.invoker.Invocation;
import github.qyqd.rpc.invoker.RpcInvocation;

import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ClassName ConsistentLoadBalance
 * @Description 直接从dubbo里拿过来的
 * @Author 潘语笛
 * @Date 14/1/2022 上午10:57
 * Version 1.0
 */
public class ConsistentLoadBalance implements LoadBalance{
    Map<String, ConsistentHashSelector> selectors = new ConcurrentHashMap<>();
    private ThreadLocal<Integer> preIdx = new ThreadLocal<>();
    @Override
    public Invocation choose(List<Invocation> invocationList) {
        int identityHashCode = invocationList.hashCode();
        // build rpc service name by rpcRequest
        Invocation invocation = invocationList.get(0);
        String serviceName = invocation.getServiceName();
        ConsistentHashSelector selector = selectors.get(serviceName);
        // check for updates
        if (selector == null || selector.identityHashCode != identityHashCode) {
            selectors.put(serviceName, new ConsistentHashSelector(invocationList, identityHashCode));
            selector = selectors.get(serviceName);
        }
        preIdx.set(0);
        return selector.select(getKey(invocationList), 0);
    }
    private String getKey(List<Invocation> invocationList) {
        // build rpc service name by rpcRequest
        Invocation invocation = invocationList.get(0);
        Invocation key = new RpcInvocation();
        key.setParameters(invocation.getParameters());
        key.setMethodName(invocation.getMethodName());
        key.setInterfaceName(invocation.getInterfaceName());
        return key.toString();
    }
    @Override
    public Invocation chooseNext(List<Invocation> invocationList) {
        // build rpc service name by rpcRequest
        Invocation invocation = invocationList.get(0);
        String serviceName = invocation.getServiceName();
        ConsistentHashSelector selector = selectors.get(serviceName);
        preIdx.set(preIdx.get() + 1);
        selector = selectors.get(serviceName);
        return selector.select(getKey(invocationList), 0);
    }

    private static final class ConsistentHashSelector<T> {

        private final TreeMap<Long, Invocation> virtualInvokers;


        private final int replicaNumber = 160;

        private final int identityHashCode;

        private final int preChooseIdx = 0;
        ConsistentHashSelector(List<Invocation> invokers, int identityHashCode) {
            this.virtualInvokers = new TreeMap<Long, Invocation>();
            this.identityHashCode = identityHashCode;
            for (Invocation invoker : invokers) {
                String address = invoker.getUrl();
                for (int i = 0; i < replicaNumber / 4; i++) {
                    byte[] digest = md5(address + i);
                    for (int h = 0; h < 4; h++) {
                        long m = hash(digest, h);
                        virtualInvokers.put(m, invoker);
                    }
                }
            }
        }

        public Invocation select(String invocation, Integer idx) {
            String key = invocation;
            byte[] digest = md5(key);
            return selectForKey(hash(digest, idx));
        }

        private Invocation selectForKey(long hash) {
            Map.Entry<Long, Invocation> entry = virtualInvokers.ceilingEntry(hash);
            if (entry == null) {
                entry = virtualInvokers.firstEntry();
            }
            return entry.getValue();
        }

        private long hash(byte[] digest, int number) {
            return (((long) (digest[3 + number * 4] & 0xFF) << 24)
                    | ((long) (digest[2 + number * 4] & 0xFF) << 16)
                    | ((long) (digest[1 + number * 4] & 0xFF) << 8)
                    | (digest[number * 4] & 0xFF))
                    & 0xFFFFFFFFL;
        }

        private byte[] md5(String value) {
            MessageDigest md5;
            try {
                md5 = MessageDigest.getInstance("MD5");
            } catch (NoSuchAlgorithmException e) {
                throw new IllegalStateException(e.getMessage(), e);
            }
            md5.reset();
            byte[] bytes = value.getBytes(StandardCharsets.UTF_8);
            md5.update(bytes);
            return md5.digest();
        }

    }
}
