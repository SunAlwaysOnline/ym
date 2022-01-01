package com.yang.ym.testThreadLocal;

/**
 * @author qcy
 * @create 2021/08/15 16:39:37
 */
public class Main {

    private static ThreadLocal<Integer> tl = new ThreadLocal<>();

//    public static void main(String[] args) {
//
//        //tl.set(1);
//
//        Thread t = new Thread(() -> {
//            tl.set(2);
//            System.out.println("子线程:" + tl.get());
//        });
//        t.start();
//
//        System.out.println("主线程:" + tl.get());
//
//    }

    public static void main(String[] args) {
        InheritableThreadLocal<Integer> threadLocal = new InheritableThreadLocal<>();

        threadLocal.set(1);
        System.out.println("父线程的副本值:" + threadLocal.get());

        new Thread(() -> System.out.println("子线程的副本值:" + threadLocal.get())).start();
    }


}
