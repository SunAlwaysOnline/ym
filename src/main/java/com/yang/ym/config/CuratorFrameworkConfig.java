package com.yang.ym.config;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author qcy
 * @create 2022/01/01 22:59:34
 */
@Configuration
public class CuratorFrameworkConfig {

    //zk各节点地址
    private static final String CONNECT_STRING = "localhost:2181,localhost:2182,localhost:2183";
    //连接超时时间(单位:毫秒)
    private static final int CONNECTION_TIME_OUT_MS = 10 * 1000;
    //会话超时时间(单位:毫秒)
    private static final int SESSION_TIME_OUT_MS = 30 * 1000;
    //重试的初始等待时间(单位:毫秒)
    private static final int BASE_SLEEP_TIME_MS = 2 * 1000;
    //最大重试次数
    private static final int MAX_RETRIES = 3;

    @Bean
    public CuratorFramework getCuratorFramework() {
        CuratorFramework curatorFramework = CuratorFrameworkFactory.builder()
                .connectString(CONNECT_STRING)
                .connectionTimeoutMs(CONNECTION_TIME_OUT_MS)
                .sessionTimeoutMs(SESSION_TIME_OUT_MS)
                .retryPolicy(new ExponentialBackoffRetry(BASE_SLEEP_TIME_MS, MAX_RETRIES))
                .build();
        curatorFramework.start();
        return curatorFramework;
    }

}
