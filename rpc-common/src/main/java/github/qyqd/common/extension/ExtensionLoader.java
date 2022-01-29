package github.qyqd.common.extension;

import github.qyqd.common.exception.ExtensionLoadException;
import github.qyqd.common.exception.RpcException;
import github.qyqd.common.utils.Holder;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Enumeration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: 潘语笛
 * @Date: 25/1/2022 下午2:16
 * @Description: 模仿dubbo实现SPI机制，简单版
 */
@Slf4j
public final class ExtensionLoader<T> {
    private static final String SERVICE_DIRECTORY = "META-INF/extensions/";
    /**
     * ExtensionLoader缓存
     */
    private static final Map<Class, ExtensionLoader<Object>> EXTENSION_LOADER_CACHE = new ConcurrentHashMap<>();
    /**
     * 实例缓存
     */
    private final Map<String, Holder> INSTANCE_CACHE = new ConcurrentHashMap<>();

    /**
     * 所有类型
     */
    private final Map<String, Class> CLAZZ_MAP = new ConcurrentHashMap<>();
    /**
     * 当前加载器的类型
     */
    Class<?> type;
    private ExtensionLoader(Class clazz) {
        this.type = clazz;
    }
    /**
     * 获取接口对应的ExtensionLoader
     * @param clazz
     * @param <S>
     * @return
     */
    public static <S> ExtensionLoader<S> getExtensionLoader(Class<S> clazz) {

        if(clazz == null) {
            throw new IllegalArgumentException("extension class should not be null");
        }

        if(!clazz.isInterface()) {
            throw new IllegalArgumentException("extension class should be an interface");
        }

        if(clazz.getDeclaredAnnotation(SPI.class) == null) {
            throw new IllegalArgumentException("extension interface should be annotated with SPI " + clazz.getName());
        }
        if(!EXTENSION_LOADER_CACHE.containsKey(clazz)) {
            synchronized (EXTENSION_LOADER_CACHE) {
                if(!EXTENSION_LOADER_CACHE.containsKey(clazz)) {
                    EXTENSION_LOADER_CACHE.put(clazz, new ExtensionLoader<>(clazz));
                }
            }
        }
        return (ExtensionLoader<S>) EXTENSION_LOADER_CACHE.get(clazz);
    }

    /**
     * 通过key获取类型对应的
     * @param name
     * @return
     */
    public T getExtension(String name) {
        INSTANCE_CACHE.putIfAbsent(name, new Holder());
        Holder<T> holder = INSTANCE_CACHE.get(name);
        if(holder.get() == null) {
            synchronized (holder) {
                if(holder.get() == null) {
                    holder.set(createExtension(name));
                }
            }
        }
        if(!INSTANCE_CACHE.containsKey(name)) {
            throw new ExtensionLoadException("cannot find extension　" + name);
        }
        return (T) INSTANCE_CACHE.get(name).get();
    }

    public T getExtension() {
        SPI declaredAnnotation = type.getDeclaredAnnotation(SPI.class);
        if(declaredAnnotation.value() == null || declaredAnnotation.value().isEmpty()) {
            throw new IllegalArgumentException("SPI annotation need a default value");
        }
        return getExtension(declaredAnnotation.value());
    }
    /**
     * 读取所有类型并返回一个需要的实例
     * @param name
     * @return
     */
    private T createExtension(String name) {
        if(CLAZZ_MAP.isEmpty()) {
            synchronized (CLAZZ_MAP) {
                if(CLAZZ_MAP.isEmpty()) {
                    loadDirectory();
                }
            }
        }
        Class clazz = CLAZZ_MAP.get(name);
        try {
            return (T) clazz.newInstance();
        } catch (InstantiationException e) {
            log.info("create extension failed {}", e);
            throw new RpcException("create extension failed");
        } catch (IllegalAccessException e) {
            log.info("create extension failed {}", e);
            throw new RpcException("create extension failed");
        }
    }

    /**
     * 从资源文件中读取所有类型
     */
    private void loadDirectory() {
        String fileName = this.type.getName();
        String filePath = SERVICE_DIRECTORY + fileName;
        ClassLoader classLoader = this.getClass().getClassLoader();
        try {
            Enumeration<URL> resources = classLoader.getResources(filePath);
            while(resources.hasMoreElements()) {
                URL url = resources.nextElement();
                loadResource(url);
            }
        } catch (IOException e) {
            log.info("read resource file failed {}", e);
        }
    }

    private void loadResource(URL url) {
        InputStream inputStream = null;
        BufferedReader br = null;
        try {
            inputStream = url.openStream();
            br = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while((line = br.readLine()) != null) {
                String[] split = line.split("=");
                if(split.length != 2) {
                    log.info("the format of the extension file is incorrect {}", type.getName());
                    continue;
                }
                String key = split[0];
                String clazzName = split[1];
                Class<?> aClass = Class.forName(clazzName);
                CLAZZ_MAP.put(key, aClass);
            }
        } catch (IOException e) {
            log.info("read resource file failed {}", e);
        } catch (ClassNotFoundException e) {
            log.info("can not find class {}", e);
        } finally {
            try {
                if(br != null) {
                    br.close();
                }
                if(inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                log.info("close inputstream failed {}", e);
            }
        }

    }
}
