package com.yang.ym.redis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author qcy
 * @create 2021/08/25 22:39:25
 */
@Slf4j
@Component
public class RedisLock {

    @Resource
    StringRedisTemplate template;

    public void handle1(String key, int expireTime) throws InterruptedException {
        //加锁,我们并不关心value的值
        while (!template.opsForValue().setIfAbsent(key, "")) {
            Thread.sleep(1000);
        }

        //设置过期时间,单位为秒
        template.expire(key, expireTime, TimeUnit.SECONDS);
        try {
            //执行业务代码
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //释放锁
            template.delete(key);
        }
    }

    public void handle2(String key, int expireTime) throws InterruptedException {
        //加锁并设置过期时间
        while (!template.opsForValue().setIfAbsent(key, "", expireTime, TimeUnit.SECONDS)) {
            Thread.sleep(1000);
        }

        try {
            //执行业务代码
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //释放锁
            template.delete(key);
        }
    }

    //续期子线程
    class RenewalThread extends Thread {
        String key;

        RenewalThread(String key) {
            this.key = key;
        }

        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    this.interrupt();
                }
                long ttl = template.getExpire(key, TimeUnit.SECONDS);
                template.expire(key, ttl + 2, TimeUnit.SECONDS);
            }
        }
    }

    public void handle3(String key, int expireTime, TimeUnit timeUnit) throws InterruptedException {
        //加锁并设置过期时间
        while (!template.opsForValue().setIfAbsent(key, "", expireTime, timeUnit)) {
            Thread.sleep(1000);
        }

        RenewalThread thread = new RenewalThread(key);
        try {
            //续期子线程,这里只是做个演示
            thread.start();

            //执行业务代码
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //打断续期子线程
            thread.interrupt();
            //释放锁
            template.delete(key);
        }
    }

    public void handle4(String key, int expireTime, TimeUnit timeUnit) throws InterruptedException {
        //唯一标识可以是机器id+线程id
        String value = getWorkerId() + Thread.currentThread().getId();
        while (!template.opsForValue().setIfAbsent(key, value, expireTime, timeUnit)) {
            Thread.sleep(1000);
        }

        try {
            //执行业务代码
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //释放锁前进行检验
            if (Objects.equals(template.opsForValue().get(key), key)) {
                template.delete(key);
            }
        }
    }

    public void handle5(String key, int expireTime, TimeUnit timeUnit) throws InterruptedException {
        //唯一标识可以是机器id+线程id
        String value = getWorkerId() + Thread.currentThread().getId();
        while (!template.opsForValue().setIfAbsent(key, value, expireTime, timeUnit)) {
            Thread.sleep(1000);
        }

        try {
            //执行业务代码
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //释放锁前进行检验
            del(key, value);
        }
    }

    private void del(String key, String value) {
        String script = "if redis.call('get', KEYS[1]) == KEYS[2] then return redis.call('del', KEYS[1]) else return 0 end";
        RedisScript<Long> redisScript = new DefaultRedisScript<>(script, Long.class);
        template.execute(redisScript, Arrays.asList(key, value));
    }


    private String getWorkerId() {
        new Thread(() -> {
            System.out.println(1);
        }).start();
        return "";
    }

    public boolean tryLock(String key, String value, int expireTime, TimeUnit timeUnit) {
        Boolean flag = template.opsForValue().setIfAbsent(key, value, expireTime, timeUnit);
        if (flag == null || !flag) {
            log.info("申请锁(" + key + "," + value + ")失败");
            return false;
        }
        log.error("申请锁(" + key + "," + value + ")成功");
        return true;
    }

    public void unLock(String key, String value) {
        String script = "if redis.call('get', KEYS[1]) == KEYS[2] then return redis.call('del', KEYS[1]) else return 0 end";
        RedisScript<Long> redisScript = new DefaultRedisScript<>(script, Long.class);
        Long result = template.execute(redisScript, Arrays.asList(key, value));
        if (result == null || result == 0) {
            log.info("释放锁(" + key + "," + value + ")失败,该锁不存在或锁已经过期");
        } else {
            log.info("释放锁(" + key + "," + value + ")成功");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new RedisLock().getWorkerId();
        Thread.sleep(10000);
    }

}
