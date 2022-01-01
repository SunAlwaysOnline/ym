package com.yang.ym.service;

//import org.redisson.api.RLock;
//import org.redisson.api.RedissonClient;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author qcy
 * @create 2021/12/12 20:20:39
 */
@Service
public class TestRedis {
//    @Resource
//    RedissonClient redissonClient;
//
//    public void lock() {
//        RLock lock = redissonClient.getLock("SunAlwaysOnline");
//        lock.lock();
//        try {
//            //模拟业务耗时
//            Thread.sleep(60000);
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            lock.unlock();
//        }
//    }
}


//if (redis.call('exists', KEYS[1]) == 0) then
//    redis.call('hincrby', KEYS[1], ARGV[2], 1);
//    redis.call('pexpire', KEYS[1], ARGV[1]);
//    return nil;
//end;
//if (redis.call('hexists', KEYS[1], ARGV[2]) == 1) then
//    redis.call('hincrby', KEYS[1], ARGV[2], 1);
//    redis.call('pexpire', KEYS[1], ARGV[1]);
//return nil;
//end;
//return redis.call('pttl', KEYS[1]);


//if (redis.call('hexists', KEYS[1], ARGV[3]) == 0) then
//    return nil;
//end;
//local counter = redis.call('hincrby', KEYS[1], ARGV[3], -1);
//if (counter > 0) then
//    redis.call('pexpire', KEYS[1], ARGV[2]);
//    return 0;
//else
//    redis.call('del', KEYS[1]);
//    redis.call('publish', KEYS[2], ARGV[1]);
//    return 1;
//end;
//return nil