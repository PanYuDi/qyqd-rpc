package github.qyqd.rpc.invoker;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: PanYuDi
 * @Date: 21/12/2021 下午8:35
 * @Description:
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RpcInvocation implements Invocation {
    String interfaceName;
    String methodName;
    Class<?>[] parameterTypes;
    Object[] parameters;
    String url;
    @Override
    public String getInterfaceName() {
        return interfaceName;
    }

    @Override
    public String getMethodName() {
        return methodName;
    }

    @Override
    public Class<?>[] getParameterTypes() {
        return parameterTypes;
    }

    @Override
    public Object[] getParameters() {
        return parameters;
    }

    @Override
    public String getUrl() {
        return url;
    }
}
