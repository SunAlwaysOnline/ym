package com.yang.ym.spi;

/**
 * @author qcy
 * @create 2021/08/24 23:16:02
 */
public class FileLog implements Log {
    @Override
    public void print() {
        System.out.println("在文件里打印日志");
    }
}