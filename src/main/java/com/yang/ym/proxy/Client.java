package com.yang.ym.proxy;

/**
 * @author qcy
 * @create 2021/09/07 21:07:58
 * <p>
 * 委托人
 */

public class Client implements Action {

    @Override
    public void litigation() {
        System.out.println("委托人：我有诉讼的需求");
    }

    @Override
    public void consult() {
        System.out.println("委托人：我有咨询的需求");
    }

    @Override
    public String toString() {
        return "1";
    }
}
