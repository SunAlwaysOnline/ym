package com.yang.ym.testDepend;

import com.yang.ym.testBean.A;
import org.junit.Test;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.lang.Nullable;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.function.Supplier;

/**
 * @author qcy
 * @create 2021/10/01 21:10:19
 */
public class DependTest {

    @Test
    public void get() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
        A a = (A) context.getBean("a");
        System.out.println("关闭容器");
        context.close();
    }

//    protected Object createBean(String beanName, RootBeanDefinition mbd, @Nullable Object[] args)
//            throws BeanCreationException {
//
//        RootBeanDefinition mbdToUse = mbd;
//
//        Class<?> resolvedClass = resolveBeanClass(mbd, beanName);
//        if (resolvedClass != null && !mbd.hasBeanClass() && mbd.getBeanClassName() != null) {
//            mbdToUse = new RootBeanDefinition(mbd);
//            mbdToUse.setBeanClass(resolvedClass);
//        }
//
//        // Prepare method overrides.
//        mbdToUse.prepareMethodOverrides();
//
//        // Give BeanPostProcessors a chance to return a proxy instead of the target bean instance.
//        Object bean = resolveBeforeInstantiation(beanName, mbdToUse);
//        if (bean != null) {
//            return bean;
//        }
//
//        Object beanInstance = doCreateBean(beanName, mbdToUse, args);
//
//        return beanInstance;
//    }

//    public Object getSingleton(String beanName, ObjectFactory<?> singletonFactory) {
//        //如果不在缓存中，就会利用getObject方法去创建
//        synchronized (this.singletonObjects) {
//            Object singletonObject = this.singletonObjects.get(beanName);
//            if (singletonObject == null) {
//                //标志当前bean正在创建中，如果被多次创建，这里也会抛出异常
//                beforeSingletonCreation(beanName);
//                boolean newSingleton = false;
//                //执行getObject，即执行外部传入的createBean方法
//                singletonObject = singletonFactory.getObject();
//                newSingleton = true;
//                //省略异常处理，出现异常时，newSingleton=false
//                //取消bean正在创建的标志
//                afterSingletonCreation(beanName);
//                if (newSingleton) {
//                    //管理缓存，后面会说
//                    addSingleton(beanName, singletonObject);
//                }
//            }
//            return singletonObject;
//        }
//    }

//    protected Object doCreateBean(final String beanName, final RootBeanDefinition mbd, final @Nullable Object[] args)
//            throws BeanCreationException {
//
//        // 实例化bean
//        BeanWrapper instanceWrapper = createBeanInstance(beanName, mbd, args);
//
//        boolean earlySingletonExposure = (mbd.isSingleton() && this.allowCircularReferences &&
//                isSingletonCurrentlyInCreation(beanName));
//        if (earlySingletonExposure) {
//            //加入到三级缓存中，getEarlyBeanReference会返回单例工厂
//            addSingletonFactory(beanName, () -> getEarlyBeanReference(beanName, mbd, bean));
//        }
//
//        Object exposedObject = bean;
//        //属性注入
//        populateBean(beanName, mbd, instanceWrapper);
//        //初始化
//        exposedObject = initializeBean(beanName, exposedObject, mbd);
//
//        if (earlySingletonExposure) {
//            //从二级缓存中查找
//            Object earlySingletonReference = getSingleton(beanName, false);
//            if (earlySingletonReference != null) {
//                //返回二级缓存中的bean，这里就有可能是代理后的对象
//                exposedObject = earlySingletonReference;
//
//            }
//        }
//        return exposedObject;
//    }
//
//    protected Object getEarlyBeanReference(String beanName, RootBeanDefinition mbd, Object bean) {
//        Object exposedObject = bean;
//        //从容器中寻找实现InstantiationAwareBeanPostProcessor接口的后置处理器
//        if (!mbd.isSynthetic() && hasInstantiationAwareBeanPostProcessors()) {
//            //遍历找到的所有符合要求的后置处理器
//            for (BeanPostProcessor bp : getBeanPostProcessors()) {
//                //如果后置处理器实现了SmartInstantiationAwareBeanPostProcessor接口
//                if (bp instanceof SmartInstantiationAwareBeanPostProcessor) {
//                    SmartInstantiationAwareBeanPostProcessor ibp = (SmartInstantiationAwareBeanPostProcessor) bp;
//                    //调用SmartInstantiationAwareBeanPostProcessor的getEarlyBeanReference方法
//                    exposedObject = ibp.getEarlyBeanReference(exposedObject, beanName);
//                }
//            }
//        }
//        return exposedObject;
//    }

}
