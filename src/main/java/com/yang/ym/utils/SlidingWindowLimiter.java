package com.yang.ym.utils;

import org.springframework.data.redis.core.RedisTemplate;

import java.util.LinkedList;

/**
 * @author qcy
 * @create 2021/12/04 21:12:19
 */
public class SlidingWindowLimiter {

    /**
     * 时间间隔窗口(单位:毫秒)
     */
    private long intervalWindow;
    /**
     * 窗口内的最大请求数
     */
    private int max;
    /**
     * 限流容器
     * 队列尾部保存最新通过的请求时间
     */
    private LinkedList<Long> list = new LinkedList<>();

    public SlidingWindowLimiter(int intervalWindow, int max) {
        this.intervalWindow = intervalWindow;
        this.max = max;
    }

    //省略get与set方法


    public boolean limit() {
        long now = System.currentTimeMillis();

        //队列未满,说明当前窗口还可以接收请求
        if (list.size() < max) {
            list.addLast(now);
            return true;
        }

        //队列已满
        Long first = list.getFirst();
        if (now - first <= intervalWindow) {
            //说明新请求和队列中的请求还处于一个窗口内,触发了限流
            return false;
        }

        //说明新请求和队列中的请求不在通过窗口内
        list.removeFirst();
        list.addLast(now);
        return true;
    }

//    public boolean zsetLimit(String key) {
//        RedisTemplate<String, Long> redisTemplate = new RedisTemplate<>();
//        redisTemplate.opsForZSet().removeRangeByScore()
//        Long size = redisTemplate.opsForZSet().size(key);
//        if (size < max) {
//            return true;
//        }
//
//        Long first = redisTemplate.opsForList().index(key, 0);
//
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

}
