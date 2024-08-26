package com.interface21.beans.factory.config;

import com.interface21.beans.factory.Advice;
import com.interface21.beans.factory.PointCut;
import com.interface21.beans.factory.ProxyFactoryBean;
import com.interface21.transaction.PlatformTransactionManager;
import com.interface21.transaction.annotation.Transactional;
import com.interface21.transaction.aop.TransactionAdvice;
import com.interface21.transaction.aop.TransactionalPointCut;
import java.util.Arrays;

public class TransactionProxyBeanPostProcessor implements BeanPostProcessor {

    private PointCut pointCut;
    private Advice advice;

    public TransactionProxyBeanPostProcessor(PlatformTransactionManager transactionManager) {
        this.pointCut = new TransactionalPointCut();
        this.advice = new TransactionAdvice(transactionManager);
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return requiresTransaction(clazz);
    }

    @Override
    public Object postInitialization(Object bean) {
        Class<?> clazz = bean.getClass();
        return new ProxyFactoryBean<>(clazz, advice, pointCut);
    }

    private boolean requiresTransaction(Class<?> clazz) {
        if (clazz.isAnnotationPresent(Transactional.class)) {
            return true;
        }

        return Arrays.stream(clazz.getMethods())
                     .anyMatch(method -> method.isAnnotationPresent(Transactional.class));
    }
}
