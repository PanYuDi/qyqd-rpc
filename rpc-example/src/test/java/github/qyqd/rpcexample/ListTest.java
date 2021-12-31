package github.qyqd.rpcexample;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName ListTest
 * @Description
 * @Author 潘语笛
 * @Date 31/12/2021 下午3:09
 * Version 1.0
 */
public class ListTest {
    @Test
    public void testList() {
        List<String> sList = new ArrayList<>();
        sList.add("a");
        sList.add("b");
        System.out.println(sList.toString());
    }
}
