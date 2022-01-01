package com.yang.ym.utils;

/**
 * @author qcy
 * @create 2021/12/05 20:43:53
 */
public class TokenBucketLimiter {
    /**
     * 上次请求到来的时间
     */
    private long preTime = System.currentTimeMillis();
    /**
     * 放入令牌速率,n/s
     */
    private int putRate;
    /**
     * 令牌桶容量
     */
    private int capacity;
    /**
     * 当前令牌数
     */
    private int bucket;

    public TokenBucketLimiter(int putRate, int capacity) {
        this.putRate = putRate;
        this.capacity = capacity;
    }

    //省略get与set方法

    public boolean limit() {
        long now = System.currentTimeMillis();

        //先放入令牌，再获取令牌
        bucket = Math.min(capacity, bucket + (int) ((now - preTime) / 1000) * putRate);
        preTime = now;

        if (bucket == 0) {
            return false;
        }

        bucket--;
        return true;
    }

    public long getPreTime() {
        return preTime;
    }

    public void setPreTime(long preTime) {
        this.preTime = preTime;
    }

    public int getPutRate() {
        return putRate;
    }

    public void setPutRate(int putRate) {
        this.putRate = putRate;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getBucket() {
        return bucket;
    }

    public void setBucket(int bucket) {
        this.bucket = bucket;
    }
}
