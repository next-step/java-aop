package com.interface21.beans.factory.support;

import com.interface21.beans.factory.BeanFactory;
import com.interface21.beans.factory.ProxyFactoryBean;
import com.interface21.beans.factory.config.BeanPostProcessor;
import com.interface21.transaction.PlatformTransactionManager;
import com.interface21.transaction.TransactionMethodInterceptor;
import com.interface21.transaction.annotation.Transactional;
import java.util.Arrays;

public class TransactionProxyCreator implements BeanPostProcessor {
    private final BeanFactory beanFactory;

    public TransactionProxyCreator(final BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    @Override
    public boolean requiresProcessing(final Object bean) {
        return hasTransactionalAnnotationAtLeastOne(bean);
    }

    private boolean hasTransactionalAnnotationAtLeastOne(final Object bean) {
        Class<?> beanType = bean.getClass();
        return hasAnnotationOnClass(beanType) || hasAnnotationOnMethod(beanType);
    }

    private boolean hasAnnotationOnClass(final Class<?> beanType) {
        return beanType.getAnnotation(Transactional.class) != null;
    }

    private boolean hasAnnotationOnMethod(final Class<?> beanType) {
        return Arrays.stream(beanType.getDeclaredMethods())
                .anyMatch(method -> method.getAnnotation(Transactional.class) != null);
    }

    @Override
    public Object postInitialization(final Object bean) {
        ProxyFactoryBean proxyFactoryBean = new ProxyFactoryBean(bean.getClass());
        proxyFactoryBean.addMethodInterceptor(new TransactionMethodInterceptor(beanFactory.getBean(PlatformTransactionManager.class), bean));
        return proxyFactoryBean.getObject();
    }
}
