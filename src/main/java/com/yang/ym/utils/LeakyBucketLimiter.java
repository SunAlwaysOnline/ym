package com.yang.ym.utils;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author qcy
 * @create 2021/12/04 23:15:43
 */
public class LeakyBucketLimiter {
    /**
     * 上次请求到来的时间
     */
    private long preTime = System.currentTimeMillis();
    /**
     * 漏水速率,n/s
     */
    private int leakRate;
    /**
     * 储蓄桶容量
     */
    private int capacity;
    /**
     * 当前水量
     */
    private int water;

    public LeakyBucketLimiter(int leakRate, int capacity) {
        this.leakRate = leakRate;
        this.capacity = capacity;
    }

    //省略get与set方法

    public boolean limit() {
        long now = System.currentTimeMillis();

        //先漏水,计算剩余水量
        water = Math.max(0, water - (int) ((now - preTime) / 1000) * leakRate);
        preTime = now;

        //桶未满
        if (water + 1 <= capacity) {
            water++;
            return true;
        }

        return false;
    }

    public void pool() {
        //最大任务数为Integer.MAX_VALUE,即储蓄桶容量
        ScheduledExecutorService pool = Executors.newScheduledThreadPool(30);
        //每隔0.1秒处理1个请求,即流速为10/s
        pool.scheduleAtFixedRate(() -> System.out.println("处理请求"), 0, 100, TimeUnit.MILLISECONDS);
    }


}
