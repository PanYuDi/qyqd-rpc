package github.qyqd.scanner;

/**
 * @ClassName AbstractScanner
 * @Description 抽象扫描器，实现扫描后回调
 * @Author 潘语笛
 * @Date 22/12/2021 下午3:35
 * Version 1.0
 */
public abstract class AbstractScanner implements Scanner{
    @Override
    public int scan(String[] basePackages, ScanAction scanAction) {
        ServiceInfo[] all = findAll(basePackages);
        for(ServiceInfo serviceInfo:all) {
            scanAction.doAction(serviceInfo);
        }
        return all.length;
    }

    /**
     * 找到所有的服务
     * @param basePackages
     * @return
     */
    public abstract ServiceInfo[] findAll(String[] basePackages);

}
