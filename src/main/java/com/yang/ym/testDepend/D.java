package com.yang.ym.testDepend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author qcy
 * @create 2021/10/02 22:13:22
 */
@Component
public class D {
    @Autowired
    private C c;
}
