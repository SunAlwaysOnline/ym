package com.yang.ym.spi;

/**
 * @author qcy
 * @create 2021/08/24 23:15:39
 */
public class ConsoleLog implements Log {

    @Override
    public void print() {
        System.out.println("在控制台里打印日志");
    }
}
