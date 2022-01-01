package com.yang.ym.testDepend;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author qcy
 * @create 2021/10/02 13:37:02
 */
public class Main {

//    //由类名可以获取到对应的实例对象
//    private static Map<String, Object> singletonObjects = new HashMap<>();
//
//    private static <T> T getBean(Class<T> clazz) throws IllegalAccessException, InstantiationException {
//        //先从缓存中获取
//        String className = clazz.getSimpleName();
//        if (singletonObjects.containsKey(className)) {
//            return (T) singletonObjects.get(className);
//        }
//
//        //实例化对象
//        T instance = clazz.newInstance();
//        //实例化完成后，就讲这个半成品放入到缓存中
//        singletonObjects.put(className, instance);
//
//        //获取当前类中的所有字段
//        Field[] fields = clazz.getDeclaredFields();
//        for (Field field : fields) {
//            //允许访问私有变量
//            field.setAccessible(true);
//            //判断字段是否被@Load注解修饰
//            boolean isUseLoad = field.isAnnotationPresent(Load.class);
//            if (!isUseLoad) {
//                continue;
//            }
//            //获取需要被注入的字段的class
//            Class<?> fieldType = field.getType();
//            //递归获取字段的实例对象
//            Object fieldBean = getBean(fieldType);
//            //将实例对象注入到该字段中
//            field.set(instance, fieldBean);
//        }
//
//        return instance;
//    }


    //成品缓存
    private static final Map<String, Object> singletonObjects = new ConcurrentHashMap<>();
    //半成品缓存
    private static final Map<String, Object> earlySingletonObjects = new ConcurrentHashMap<>();

    //从缓存中获取
    private static Object getSingleton(String className) {
        //先从成品缓存中查找
        Object singletonObject = singletonObjects.get(className);
        if (singletonObject == null) {
            //再从半成品缓存中查找
            singletonObject = earlySingletonObjects.get(className);
        }
        return singletonObject;
    }

    @SuppressWarnings("unchecked")
    private static <T> T getBean(Class<T> clazz) throws IllegalAccessException, InstantiationException {
        //先从缓存中获取
        String className = clazz.getSimpleName();
        Object singleton = getSingleton(className);
        if (singleton != null) {
            return (T) singleton;
        }

        synchronized (singletonObjects) {
            singleton = singletonObjects.get(className);
            //这里需要再进行一次检查
            if (singleton != null) {
                return (T) singleton;
            }

            //实例化对象
            T instance = clazz.newInstance();
            //实例化完成后，就将这个半成品放入到缓存中
            earlySingletonObjects.put(className, instance);

            //获取当前类中的所有字段
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                //允许访问私有变量
                field.setAccessible(true);
                //判断字段是否被@Load注解修饰
                boolean isUseLoad = field.isAnnotationPresent(Load.class);
                if (!isUseLoad) {
                    continue;
                }
                //获取需要被注入的字段的class
                Class<?> fieldType = field.getType();
                //递归获取字段的实例对象
                Object fieldBean = getBean(fieldType);
                //将实例对象注入到该字段中
                field.set(instance, fieldBean);
            }

            //完成属性注入后，从半成品缓存中移除，加入到成品缓存中
            earlySingletonObjects.remove(className);
            singletonObjects.put(className, instance);

            return instance;
        }
    }


    public static void main(String[] args) {
        new Thread(() -> {
            try {
                A a1 = getBean(A.class);
                System.out.println("t1.a:" + a1.hashCode());
                System.out.println("t1.b:" + a1.getB().hashCode());
            } catch (IllegalAccessException | InstantiationException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            try {
                A a1 = getBean(A.class);
                System.out.println("t2.a:" + a1.hashCode());
                System.out.println("t2.b:" + a1.getB().hashCode());
            } catch (IllegalAccessException | InstantiationException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            try {
                B b = getBean(B.class);
                System.out.println("t3.b:" + b.hashCode());
                System.out.println("t3.a:" + b.getA().hashCode());
            } catch (IllegalAccessException | InstantiationException e) {
                e.printStackTrace();
            }
        }).start();
    }

}
