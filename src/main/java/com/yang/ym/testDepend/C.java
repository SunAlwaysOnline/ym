package com.yang.ym.testDepend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author qcy
 * @create 2021/10/02 22:12:33
 */
@Component
public class C {

    private D d;

//    public C() {
//        System.out.println("执行c的无参构造函数");
//    }

//    public C(D d) {
//        System.out.println("执行c的构造函数");
//        this.d = d;
//    }
}
