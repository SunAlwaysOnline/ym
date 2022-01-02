package com.yang.ym.zk;

import com.google.common.collect.Maps;
import org.apache.curator.RetryLoop;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.imps.CuratorFrameworkState;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.framework.recipes.locks.PredicateResults;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.utils.ThreadUtils;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

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


    //    public void acquire() throws Exception {
//        if (!internalLock(-1, null)) {
//            throw new IOException("Lost connection while trying to acquire lock: " + basePath);
//        }
//    }
//
//    private boolean internalLock(long time, TimeUnit unit) throws Exception {
//        Thread currentThread = Thread.currentThread();
//        //通过能否在map中取到该线程的LockData信息,来判断该线程是否已经持有锁
//        LockData lockData = threadData.get(currentThread);
//        if (lockData != null) {
//            //进行可重入,直接返回加锁成功
//            lockData.lockCount.incrementAndGet();
//            return true;
//        }
//        //进行加锁
//        String lockPath = internals.attemptLock(time, unit, getLockNodeBytes());
//        if (lockPath != null) {
//            //加锁成功,保存到map中
//            LockData newLockData = new LockData(currentThread, lockPath);
//            threadData.put(currentThread, newLockData);
//            return true;
//        }
//
//        return false;
//    }
//
//    private final ConcurrentMap<Thread, LockData> threadData = Maps.newConcurrentMap();
//
//    private static class LockData {
//        final Thread owningThread;
//        final String lockPath;
//        final AtomicInteger lockCount = new AtomicInteger(1);
//
//        private LockData(Thread owningThread, String lockPath) {
//            this.owningThread = owningThread;
//            this.lockPath = lockPath;
//        }
//    }
//
//    String attemptLock(long time, TimeUnit unit, byte[] lockNodeBytes) throws Exception {
//        //开始时间
//        final long startMillis = System.currentTimeMillis();
//        //将超时时间统一转化为毫秒单位
//        final Long millisToWait = (unit != null) ? unit.toMillis(time) : null;
//        //节点数据,这里为null
//        final byte[] localLockNodeBytes = (revocable.get() != null) ? new byte[0] : lockNodeBytes;
//        //重试次数
//        int retryCount = 0;
//        //锁路径
//        String ourPath = null;
//        //是否获取到锁
//        boolean hasTheLock = false;
//        //是否完成
//        boolean isDone = false;
//
//        while (!isDone) {
//            isDone = true;
//
//            try {
//                //创建一个临时有序节点，并返回节点路径
//                //内部调用client.create().creatingParentContainersIfNeeded().withProtection().withMode(CreateMode.EPHEMERAL_SEQUENTIAL).forPath(path);
//                ourPath = driver.createsTheLock(client, path, localLockNodeBytes);
//                //依据返回的节点路径,判断是否抢到了锁
//                hasTheLock = internalLockLoop(startMillis, millisToWait, ourPath);
//            } catch (KeeperException.NoNodeException e) {
//                //在会话过期时,可能导致driver找不到临时有序节点,从而抛出NoNodeException
//                //这里就进行重试
//                if (client.getZookeeperClient().getRetryPolicy().allowRetry(retryCount++, System.currentTimeMillis() - startMillis, RetryLoop.getDefaultRetrySleeper())) {
//                    isDone = false;
//                } else {
//                    throw e;
//                }
//            }
//        }
//        //获取到锁,则返回节点路径,供调用方记录到map中
//        if (hasTheLock) {
//            return ourPath;
//        }
//
//        return null;
//    }

//    private boolean internalLockLoop(long startMillis, Long millisToWait, String ourPath) throws Exception {
//        //是否获取到锁
//        boolean haveTheLock = false;
//        boolean doDelete = false;
//        try {
//            if (revocable.get() != null) {
//                //当前不会进入这里
//                client.getData().usingWatcher(revocableWatcher).forPath(ourPath);
//            }
//            //一直尝试获取锁
//            while ((client.getState() == CuratorFrameworkState.STARTED) && !haveTheLock) {
//                //返回basePath(这里是lockqcy)下所有的临时有序节点,并且按照后缀从小到大排列
//                List<String> children = getSortedChildren();
//                //取出当前线程创建出来的临时有序节点的名称,这里就是/_c_c46513c3-ace0-405f-aa1e-a531ce28fb47-lock-0000000005
//                String sequenceNodeName = ourPath.substring(basePath.length() + 1);
//                //判断当前节点是否处于排序后的首位,如果处于首位,则代表获取到了锁
//                PredicateResults predicateResults = driver.getsTheLock(client, children, sequenceNodeName, maxLeases);
//                if (predicateResults.getsTheLock()) {
//                    //获取到锁之后,则终止循环
//                    haveTheLock = true;
//                } else {
//                    //这里代表没有获取到锁
//                    //获取比当前节点索引小的前一个节点
//                    String previousSequencePath = basePath + "/" + predicateResults.getPathToWatch();
//
//                    synchronized (this) {
//                        try {
//                            //如果前一个节点不存在,则直接抛出NoNodeException,catch中不进行处理,在下一轮中继续获取锁
//                            //如果前一个节点存在,则给它设置一个监听器,监听它的释放事件
//                            client.getData().usingWatcher(watcher).forPath(previousSequencePath);
//                            if (millisToWait != null) {
//                                millisToWait -= (System.currentTimeMillis() - startMillis);
//                                startMillis = System.currentTimeMillis();
//                                //判断是否超时
//                                if (millisToWait <= 0) {
//                                    //获取锁超时,删除刚才创建的临时有序节点
//                                    doDelete = true;
//                                    break;
//                                }
//                                //没超时的话,在millisToWait内进行等待
//                                wait(millisToWait);
//                            } else {
//                                //无限期阻塞等待,监听到前一个节点被删除时,才会触发唤醒操作
//                                wait();
//                            }
//                        } catch (KeeperException.NoNodeException e) {
//                            //如果前一个节点不存在,则直接抛出NoNodeException,catch中不进行处理,在下一轮中继续获取锁
//                        }
//                    }
//                }
//            }
//        } catch (Exception e) {
//            ThreadUtils.checkInterrupted(e);
//            doDelete = true;
//            throw e;
//        } finally {
//            if (doDelete) {
//                //删除刚才创建出来的临时有序节点
//                deleteOurPath(ourPath);
//            }
//        }
//        return haveTheLock;
//    }
//
//    public PredicateResults getsTheLock(CuratorFramework client, List<String> children, String sequenceNodeName, int maxLeases) throws Exception {
//        //获取当前节点在所有子节点排序后的索引位置
//        int ourIndex = children.indexOf(sequenceNodeName);
//        //判断当前节点是否处于子节点中
//        validateOurIndex(sequenceNodeName, ourIndex);
//        //InterProcessMutex的构造方法,会将maxLeases初始化为1
//        //ourIndex必须为0,才能使得getsTheLock为true,也就是说,当前节点必须是basePath下的最小节点,才能代表获取到了锁
//        boolean getsTheLock = ourIndex < maxLeases;
//        //如果获取不到锁,则返回上一个节点的名称,用作对其设置监听
//        String pathToWatch = getsTheLock ? null : children.get(ourIndex - maxLeases);
//
//        return new PredicateResults(pathToWatch, getsTheLock);
//    }
//
//    static void validateOurIndex(String sequenceNodeName, int ourIndex) throws KeeperException {
//        if (ourIndex < 0) {
//            //可能会由于连接丢失导致临时节点被删除,因此这里属于保险措施
//            throw new KeeperException.NoNodeException("Sequential path not found: " + sequenceNodeName);
//        }
//    }
//
//
//    private final Watcher watcher = new Watcher() {
//        //回调方法
//        @Override
//        public void process(WatchedEvent event) {
//            notifyFromWatcher();
//        }
//    };
//
//    private synchronized void notifyFromWatcher() {
//        //唤醒所以在LockInternals实例上等待的线程
//        notifyAll();
//    }

//    public void release() throws Exception {
//        Thread currentThread = Thread.currentThread();
//        LockData lockData = threadData.get(currentThread);
//        if (lockData == null) {
//            throw new IllegalMonitorStateException("You do not own the lock: " + basePath);
//        }
//
//        int newLockCount = lockData.lockCount.decrementAndGet();
//        //直接减少一次重入次数
//        if (newLockCount > 0) {
//            return;
//        }
//        if (newLockCount < 0) {
//            throw new IllegalMonitorStateException("Lock count has gone negative for lock: " + basePath);
//        }
//
//        //到这里代表重入次数为0
//        try {
//            //释放锁
//            internals.releaseLock(lockData.lockPath);
//        } finally {
//            //从map中移除
//            threadData.remove(currentThread);
//        }
//    }
//
//    void releaseLock(String lockPath) throws Exception {
//        revocable.set(null);
//        //内部使用guaranteed,会在后台不断尝试删除节点
//        deleteOurPath(lockPath);
//    }
}
