package github.qyqd.scanner;

/**
 * @ClassName Scaner
 * @Description 服务扫描
 * @Author 潘语笛
 * @Date 22/12/2021 下午3:17
 * Version 1.0
 */
public interface Scanner {
    /**
     * 扫描
     * @param basePackages
     * @param scanAction
     * @return
     */
    public int scan(String[] basePackages, ScanAction scanAction);
    @FunctionalInterface
    static interface ScanAction {
        /**
         * 执行处理器动作
         * @param serviceInfo
         */
        public void doAction(ServiceInfo serviceInfo);
    }
}
