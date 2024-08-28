package com.interface21.transaction.interceptor;

import com.interface21.beans.factory.BeanFactory;
import com.interface21.beans.factory.ProxyFactoryBean;
import com.interface21.beans.factory.config.BeanPostProcessor;
import com.interface21.transaction.PlatformTransactionManager;
import com.interface21.transaction.annotation.Transactional;

import java.util.Arrays;

public class TransactionProxyBeanPostProcessor implements BeanPostProcessor {

    private final BeanFactory beanFactory;

    public TransactionProxyBeanPostProcessor(final BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    @Override
    public Object postInitialization(final Object bean) {
        final Class<?> beanClass = bean.getClass();
        if (shouldInit(beanClass)) {
            final ProxyFactoryBean<?> proxyFactoryBean = new ProxyFactoryBean<>(beanClass);
            final PlatformTransactionManager transactionManager = beanFactory.getBean(PlatformTransactionManager.class);
            return new TransactionProxyFactoryBean<>(proxyFactoryBean, transactionManager);
        }

        return bean;
    }

    private boolean shouldInit(final Class<?> beanClass) {
        if (beanClass.isAnnotationPresent(Transactional.class)) {
            return true;
        }
        return Arrays.stream(beanClass.getMethods()).anyMatch(method -> method.isAnnotationPresent(Transactional.class));
    }
}
