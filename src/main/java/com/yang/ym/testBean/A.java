package com.yang.ym.testBean;

import org.bouncycastle.cms.PasswordRecipientId;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.*;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * @author qcy
 * @create 2021/10/13 22:31:19
 */
public class A implements BeanNameAware, BeanClassLoaderAware, BeanFactoryAware, InitializingBean, DisposableBean {
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
        System.out.println("属性赋值");
    }

    private A() {
        System.out.println("实例化");
    }

    @PostConstruct
    public void postConstruct() {
        System.out.println("@PostConstruct指定的方法");
    }

    @Override
    public void setBeanClassLoader(ClassLoader classLoader) {
        System.out.println("BeanClassLoaderAware.setBeanClassLoader");
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        System.out.println("BeanFactoryAware.setBeanFactory");
    }

    @Override
    public void setBeanName(String name) {
        System.out.println("BeanNameAware.setBeanName");
    }

    @Override
    public void afterPropertiesSet() {
        System.out.println("InitializingBean.afterPropertiesSet");
    }

    public void initMethod() {
        System.out.println("xml中init-method指定的方法");
    }

    @PreDestroy
    public void preDestroy() {
        System.out.println("@PreDestroy指定的方法");
    }

    @Override
    public void destroy() {
        System.out.println("DisposableBean.destroy");
    }

    public void destroyMethod() {
        System.out.println("xml中destroy-method指定的方法");
    }

}
