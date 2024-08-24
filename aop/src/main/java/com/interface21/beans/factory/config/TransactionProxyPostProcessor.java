package com.interface21.beans.factory.config;

import com.interface21.beans.factory.ProxyFactoryBean;
import com.interface21.framework.Advised;
import com.interface21.framework.Advisor;
import com.interface21.framework.PointCut;
import com.interface21.transaction.PlatformTransactionManager;
import com.interface21.transaction.TransactionAfterReturningAdvice;
import com.interface21.transaction.TransactionAfterThrowingAdvice;
import com.interface21.transaction.TransactionBeforeAdvice;
import com.interface21.transaction.TransactionalPointCut;
import com.interface21.transaction.annotation.Transactional;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

public class TransactionProxyPostProcessor implements BeanPostProcessor {

    private List<Advisor> advisors;

    public TransactionProxyPostProcessor(PlatformTransactionManager platformTransactionManager) {
        PointCut pointCut = new TransactionalPointCut();
        advisors = List.of(
            new Advisor(pointCut, new TransactionBeforeAdvice(platformTransactionManager)),
            new Advisor(pointCut, new TransactionAfterReturningAdvice(platformTransactionManager)),
            new Advisor(pointCut, new TransactionAfterThrowingAdvice(platformTransactionManager)));
    }

    @Override
    public Object postInitialization(Object bean) {
        Class<?> clazz = bean.getClass();
        if (isTransactionRequired(clazz) || isTransactionRequired(clazz.getMethods())) {
            return new ProxyFactoryBean<>(new Advised(clazz, advisors));
        }
        return bean;

    }

    private boolean isTransactionRequired(Method[] methods) {
        return Arrays.stream(methods).anyMatch(method -> method.isAnnotationPresent(Transactional.class));
    }

    private boolean isTransactionRequired(Class<?> clazz) {
        return clazz.isAnnotationPresent(Transactional.class);
    }
}
