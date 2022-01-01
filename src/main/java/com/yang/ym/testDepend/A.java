package com.yang.ym.testDepend;

/**
 * @author qcy
 * @create 2021/10/02 13:34:56
 */
public class A {
    @Load
    private B b;

    public A() {

    }

    public A(B b) {
        this.b = b;
    }

    public B getB() {
        return b;
    }
}
