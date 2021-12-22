package github.qyqd.scanner;

import org.springframework.util.ClassUtils;

/**
 * @Author: 潘语笛
 * @Date: 22/12/2021 下午9:25
 * @Description: 扫描注解的服务类
 */
public class AnnotationPackageScanner extends AbstractScanner{
    @Override
    public ServiceInfo[] findAll(String[] basePackages) {
        return new ServiceInfo[0];
    }
    /**
     * 将包名转化为路径
     * @param basePackage
     * @return
     */
    private String resolveBasePackage(String basePackage) {
        return ClassUtils.convertClassNameToResourcePath(basePackage);
    }
}
