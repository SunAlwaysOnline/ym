package com.yang.ym.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author qcy
 * @create 2021/09/07 21:08:35
 */
public class LawyerHandler implements InvocationHandler {
    private Action action;

    //绑定被代理对象，最后返回代理对象的实例
    public Action bind(Action action) {
        this.action = action;
        return (Action) Proxy.newProxyInstance(
                action.getClass().getClassLoader(),
                action.getClass().getInterfaces(),
                this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //调用被代理对象的方法
        Object o = method.invoke(action, args);
        //增强方法
        System.out.println("律师处理委托人的需求");
        return o;
    }

}
