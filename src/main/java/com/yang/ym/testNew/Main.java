package com.yang.ym.testNew;

import org.openjdk.jol.info.ClassLayout;

/**
 * @author qcy
 * @create 2021/09/14 22:06:25
 */
public class Main {

    static class GirlFriend {

        String name;
        int age;

        private void eat() {

        }

        public GirlFriend() {
            System.out.println(2);
        }

        {
            System.out.println(1);
        }
    }

    private void a() {
        GirlFriend gf = new GirlFriend();
        System.out.println(gf.age);
    }

    private void b() {
        int age = 18;
        System.out.println(age);
    }

    private void a1() {
        GirlFriend gf = new GirlFriend();
        //不方便写的某些业务
    }


    public static void main(String[] args) {
        GirlFriend gf = new GirlFriend();
        System.out.println(ClassLayout.parseInstance(new Object()).toPrintable());
    }
}
