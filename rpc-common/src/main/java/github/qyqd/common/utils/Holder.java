package github.qyqd.common.utils;

import lombok.Data;

/**
 * @Author: 潘语笛
 * @Date: 25/1/2022 下午2:36
 * @Description: TODO
 */
public class Holder<T> {
    private volatile T value;

    public void set(T value) {
        this.value = value;
    }

    public T get() {
        return value;
    }
}
