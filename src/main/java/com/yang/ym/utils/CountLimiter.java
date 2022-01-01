package com.yang.ym.utils;

import org.springframework.data.redis.core.RedisTemplate;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author qcy
 * @create 2021/12/04 18:20:30
 */
public class CountLimiter {
    /**
     * 时间间隔窗口(单位:毫秒)
     */
    private long intervalWindow;
    /**
     * 该窗口内的最大请求数
     */
    private int max;
    /**
     * 当前窗口内的请求计数
     */
    private int count;
    /**
     * 当前窗口内的第一个请求的时间
     */
    private long firstReqTime = System.currentTimeMillis();

    public CountLimiter(int intervalWindow, int max) {
        this.intervalWindow = intervalWindow;
        this.max = max;
    }

    //省略get与set方法

    public boolean limit() {
        long now = System.currentTimeMillis();

        if (now > firstReqTime + intervalWindow) {
            //代表已经进入下一个窗口
            firstReqTime = now;
            count = 1;
            return true;
        }

        //还在当前的时间窗口内
        if (count + 1 <= max) {
            count++;
            return true;
        }

        return false;
    }

//    public boolean redisLimit(String key){
//        RedisTemplate<String, Integer> redisTemplate = new RedisTemplate<>();
//        Boolean result = redisTemplate.opsForValue().setIfAbsent(key, 1, intervalWindow, TimeUnit.MILLISECONDS);
//        if (result){
//            return true;
//        }
//
//        redisTemplate.opsForValue().increm
//    }

    public long getIntervalWindow() {
        return intervalWindow;
    }

    public void setIntervalWindow(long intervalWindow) {
        this.intervalWindow = intervalWindow;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public long getFirstReqTime() {
        return firstReqTime;
    }

    public void setFirstReqTime(long firstReqTime) {
        this.firstReqTime = firstReqTime;
    }

}
