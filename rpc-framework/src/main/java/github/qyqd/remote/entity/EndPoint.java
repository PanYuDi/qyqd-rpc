package github.qyqd.remote.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName EndPoint
 * @Description 连接信息
 * @Author 潘语笛
 * @Date 2021/11/9 10:48
 * Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EndPoint {
    String host;
    int port;
    int timeOut;
    int connectTimeOut;
}
