package com.yang.ym.testBean;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * @author qcy
 * @create 2021/10/13 22:02:32
 */
public class InstantiationAwareBeanPostProcessorImpl implements InstantiationAwareBeanPostProcessor {
    @Override
    public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
        if (beanName.equals("a")) {
            System.out.println("InstantiationAwareBeanPostProcessor.postProcessBeforeInstantiation");
        }
        return null;
    }

    @Override
    public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
        if (beanName.equals("a")) {
            System.out.println("InstantiationAwareBeanPostProcessor.postProcessBeforeInstantiation");
        }
        //如果这里返回false,那么所有Bean将不进行属性赋值
        return true;
    }
}
