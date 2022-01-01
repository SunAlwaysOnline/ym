package com.yang.ym.testBean;

import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * @author qcy
 * @create 2021/10/13 22:04:00
 */
public class BeanPostProcessorImpl implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (beanName.equals("a")) {
            System.out.println("BeanPostProcessor.postProcessBeforeInitialization");
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (beanName.equals("a")) {
            System.out.println("BeanPostProcessor.postProcessAfterInitialization");
        }
        return bean;
    }
}
