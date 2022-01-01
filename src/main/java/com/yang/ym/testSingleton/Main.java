package com.yang.ym.testSingleton;

import org.bouncycastle.asn1.esf.ESFAttributes;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * @author qcy
 * @create 2021/10/05 21:27:39
 */
public class Main {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Singleton instance = Singleton.getInstance();
        ObjectOutputStream ops = new ObjectOutputStream(new FileOutputStream("enum.txt"));
        ops.writeObject(instance);

        ObjectInputStream ois = new ObjectInputStream(new FileInputStream("enum.txt"));
        Object object = ois.readObject();
        System.out.println(instance == object);
    }

//    public static void main(String[] args) throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
//        //获取有参的私有构造器
//        Constructor<EnumSingleton> constructor = EnumSingleton.class.getDeclaredConstructor(String.class, int.class, Object.class);
//        //设置可以访问私有变量
//        constructor.setAccessible(true);
//        //使用私有构造器实例化对象
//        EnumSingleton singleton = constructor.newInstance();
//
//        EnumSingleton instance = EnumSingleton.INSTANCE;
//        System.out.println(singleton == instance);
//    }
}
