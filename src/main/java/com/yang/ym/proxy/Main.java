package com.yang.ym.proxy;

/**
 * @author qcy
 * @create 2021/09/07 21:09:24
 */
public class Main {
    public static void main(String[] args) {
        System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");

        Action action = new LawyerHandler().bind(new Client());
        action.litigation();
        action.consult();
        System.out.println(action.toString());
    }
}