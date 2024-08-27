package com.interface21.beans.factory;

import com.interface21.beans.factory.config.BeanPostProcessor;
import com.interface21.transaction.PlatformTransactionManager;
import com.interface21.transaction.TransactionMethodInterceptor;
import com.interface21.transaction.TransactionMethodMatcher;
import java.lang.reflect.Method;
import java.util.Arrays;

public class TransactionProxyCreator implements BeanPostProcessor {
    private final PlatformTransactionManager transactionManager;
    private final TransactionMethodMatcher transactionMethodMatcher;

    public TransactionProxyCreator(final PlatformTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
        transactionMethodMatcher = new TransactionMethodMatcher();
    }

    @Override
    public Object postInitialization(final Object bean) {
        if (isProxyRequired(bean)) {
            ProxyFactoryBean proxyFactoryBean = new ProxyFactoryBean(bean.getClass());
            proxyFactoryBean.addMethodInterceptor(new TransactionMethodInterceptor(transactionManager, bean, transactionMethodMatcher));
            return proxyFactoryBean.getObject();
        }
        return bean;
    }

    private boolean isProxyRequired(final Object bean) {
        Class<?> beanType = bean.getClass();
        return transactionMethodMatcher.hasTransactionalAnnotations(beanType)
                || hasTransactionalAnnotationAtLeastOne(beanType.getMethods());
    }

    private boolean hasTransactionalAnnotationAtLeastOne(final Method[] methods) {
        return Arrays.stream(methods)
                .anyMatch(transactionMethodMatcher::hasTransactionalAnnotations);
    }
}
