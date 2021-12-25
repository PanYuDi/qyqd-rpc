package github.qyqd.rpcexample;

import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @ClassName FutureTest
 * @Description TODO
 * @Author 潘语笛
 * @Date 20/12/2021 下午2:41
 * Version 1.0
 */
public class FutureTest {
    @Test
    public void testCompletableFuture() throws ExecutionException, InterruptedException {
        LinkedList<Integer> q = new LinkedList<>();
        CompletableFuture<String> future = new CompletableFuture<>();
        future.completeExceptionally(new Exception("lalalala"));
        future.get();
    }
}
