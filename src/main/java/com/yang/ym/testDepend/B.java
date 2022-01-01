package com.yang.ym.testDepend;

/**
 * @author qcy
 * @create 2021/10/01 21:07:16
 */
public class B {
    @Load
    private A a;

    public A getA() {
        return a;
    }
}
