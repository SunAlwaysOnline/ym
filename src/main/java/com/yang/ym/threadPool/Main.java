package com.yang.ym.threadPool;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author qcy
 * @create 2021/08/31 21:57:35
 */
public class Main {

    public static void main(String[] args) throws InterruptedException {
//        ExecutorService pool = Executors.newFixedThreadPool(2);
//
//        Thread t1 = new Thread(() -> {
//            throw new NullPointerException();
//        });
//        try {
//            pool.execute(t1);
//        } catch (Exception e) {
//            System.out.println("捕获到execute异常了");
//        }
//
//        Thread t2 = new Thread(() -> {
//            throw new ArrayIndexOutOfBoundsException();
//        });
//        Future<?> result = pool.submit(t2);
//        try {
//            result.get();
//        } catch (ExecutionException e) {
//            System.out.println("捕获到submit异常了");
//        }
//
//        pool.shutdown();


        ThreadPoolExecutor pool = new ThreadPoolExecutor(5, 10,
                1, TimeUnit.MINUTES, new ArrayBlockingQueue<>(20));

        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(pool.getQueue().size() + "," + pool.getActiveCount());
            }
        }).start();

        for (int i = 0; i < 100; i++) {
            Thread.sleep(100);
            int finalI = i;
            pool.execute(() -> {
                try {
                    Thread.sleep(1000);
                    System.out.println("finish:" + finalI);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }


    }

}
