package com.yang.ym.zk;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author qcy
 * @create 2022/01/01 21:13:51
 */
@Service
public class ZkService {

    //临时节点路径
    private static final String LOCK_PATH = "/lockqcy";

    @Resource
    CuratorFramework curatorFramework;

    public void testCurator() throws Exception {
        InterProcessMutex interProcessMutex = new InterProcessMutex(curatorFramework, LOCK_PATH);
        interProcessMutex.acquire();

        try {
            //模拟业务耗时
            Thread.sleep(30 * 1000);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            interProcessMutex.release();
        }
    }

}
