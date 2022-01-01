package com.yang.ym.testSingleton;

/**
 * @author qcy
 * @create 2021/10/05 22:11:32
 */
public enum EnumSingleton {
    //模拟单例中的数据
    INSTANCE(new Object());

    private Object data;

    EnumSingleton(Object data) {
        this.data = data;
    }

    public Object getData() {
        return data;
    }
}
