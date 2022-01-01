package com.yang.ym.testSingleton;

import java.io.Serializable;

/**
 * @author qcy
 * @create 2021/10/06 14:33:44
 */
public class Singleton implements Serializable {

    private static Singleton instance = new Singleton();

    private Singleton() {
        System.out.println(1);
        if (instance != null) {
            throw new RuntimeException("can not create singleton");
        }
    }

    public static Singleton getInstance() {
        return instance;
    }

    public Object readResolve() {
        return instance;
    }

}

//public class Singleton {
//    private static Singleton instance;
//
//    private Singleton() {
//    }
//
//    public static Singleton getInstance() {
//        if (instance == null) {
//            instance = new Singleton();
//        }
//        return instance;
//    }
//
//}

//public class Singleton implements Serializable {
//    private Singleton() {
//        if (SingletonHolder.instance != null) {
//            throw new RuntimeException("can not create singleton");
//        }
//    }
//
//    private static class SingletonHolder {
//        private static final Singleton instance = new Singleton();
//    }
//
//    public static Singleton getInstance() {
//        return SingletonHolder.instance;
//    }
//}