package com.yang.ym.testDepend;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author qcy
 * @create 2021/10/02 13:31:20
 */
//只用在字段上
@Target(ElementType.FIELD)
//运行时有效，这样可以通过反射解析注解
@Retention(RetentionPolicy.RUNTIME)
public @interface Load {
}
