package github.qyqd.rpc.remote.transport.netty.client;

import github.qyqd.common.exception.RpcException;
import github.qyqd.common.exception.TimeOutException;
import github.qyqd.common.utils.ConcurrentUtils;
import github.qyqd.rpc.remote.RequestMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @ClassName UnprocessedRequest
 * @Description 未被处理的RPC返回值，用于解决Netty异步问题
 * @Author 潘语笛
 * @Date 20/12/2021 下午1:43
 * Version 1.0
 */

public class UnprocessedRequest {
    private static AtomicInteger requestIdGenerator;
    public Map<Integer, CompletableFuture<RequestMessage>> requestFutureMap = new ConcurrentHashMap<>();
    private static volatile UnprocessedRequest singleton = null;
    private static ThreadPoolExecutor executor = ConcurrentUtils.getThreadPoolExecutor();
    private DelayQueue<TimeoutMessage> timeoutQueue = new DelayQueue<>();
    long TIME_OUT;
    private UnprocessedRequest() {
        //启动超时管理线程
        executor.execute(()->handleTimeout());
    }
    public static UnprocessedRequest getSingleton(long timeout) {
        UnprocessedRequest singleton = getSingleton();
        singleton.TIME_OUT = timeout;
        return UnprocessedRequest.singleton;
    }
    public static UnprocessedRequest getSingleton() {
        if(singleton == null) {
            synchronized (UnprocessedRequest.class) {
                if(singleton == null) {
                    singleton = new UnprocessedRequest();
                }
            }
        }
        return singleton;
    }
    public void putUnprocessedRequest(Integer requestId, CompletableFuture<RequestMessage> resultFuture) {
        requestFutureMap.put(requestId, resultFuture);
        // 放入延迟队列管理超时
        timeoutQueue.put(new TimeoutMessage(requestId, System.nanoTime() + TIME_OUT));
    }
    public RequestMessage get(String requestId) {
        if(!requestFutureMap.containsKey(requestId)) {
            throw new RpcException("get rpc result failed, requestId is " + requestId);
        }

        RequestMessage requestMessage = null;
        try {
            requestMessage = requestFutureMap.get(requestId).get();
        } catch (ExecutionException e) {
            throw new TimeOutException("server response time out, request id is" + requestId);
        } catch (InterruptedException e) {
            throw new RpcException("get rpc result failed, requestId is " + requestId);
        }
        requestFutureMap.remove(requestId);
        return requestMessage;
    }
    public void complete(RequestMessage requestMessage) {
    }
    @AllArgsConstructor
    @Getter
    static class TimeoutMessage implements Delayed {
        Integer requestId;
        long timeoutStamp;
        @Override
        public long getDelay(TimeUnit unit) {
            return unit.convert(timeoutStamp - System.nanoTime(), TimeUnit.NANOSECONDS);
        }

        @Override
        public int compareTo(Delayed o) {
            if(this.timeoutStamp - ((TimeoutMessage)o).timeoutStamp > 0) {
                return 1;
            } else if(this.timeoutStamp - ((TimeoutMessage)o).timeoutStamp == 0) {
                return 0;
            } else {
                return -1;
            }
        }
    }
    private void handleTimeout() {
        TimeoutMessage take = null;
        try {
            take = timeoutQueue.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // 超时后手动添加异常
        if(requestFutureMap.containsKey(take.getRequestId())) {
            requestFutureMap.get(take.getRequestId()).completeExceptionally(new TimeOutException("timeout"));
        }
    }
    public static Integer getRequestId() {
        return requestIdGenerator.getAndIncrement();
    }
}
