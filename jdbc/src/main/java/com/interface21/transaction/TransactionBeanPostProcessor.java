package com.interface21.transaction;

import com.interface21.beans.config.BeanFactoryBeanPostProcessor;
import com.interface21.beans.factory.BeanFactory;
import com.interface21.beans.factory.ProxyFactoryBean;
import com.interface21.beans.factory.proxy.*;
import com.interface21.transaction.annotation.Transactional;

import java.util.Arrays;

public class TransactionBeanPostProcessor implements BeanFactoryBeanPostProcessor {

    private BeanFactory beanFactory;
    private boolean classLevelTransaction = false;

    @Override
    public Object postInitialization(final Object bean) {
        if (!hasTransactionalMethods(bean)) {
            return bean;
        }

        return createTransactionProxy(bean);
    }

    @Override
    public void injectBeanFactory(final BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    private boolean hasTransactionalMethods(final Object bean) {
        final Class<?> clazz = bean.getClass();

        if (clazz.isAnnotationPresent(Transactional.class)) {
            classLevelTransaction = true;
            return true;
        }

        return Arrays.stream(clazz.getDeclaredMethods()).anyMatch(method -> method.isAnnotationPresent(Transactional.class));
    }

    private Object createTransactionProxy(final Object bean) {
        final PlatformTransactionManager transactionManager = beanFactory.getBean(PlatformTransactionManager.class);
        final ProxyFactoryBean factoryBean = new ProxyFactoryBean(beanFactory);

        final MethodBeforeAdvice methodBeforeAdvice = new TransactionBeginAdvice(transactionManager);
        final AfterReturningAdvice afterReturningAdvice = new TransactionCommitAdvice(transactionManager);
        final ThrowsAdvice throwsAdvice = new TransactionRollbackAdvice(transactionManager);

        final Pointcut pointcut = (method, targetClass) -> classLevelTransaction || method.isAnnotationPresent(Transactional.class);
        final Advisor beginAdvisor = new Advisor(methodBeforeAdvice, pointcut);
        final Advisor commitAdvisor = new Advisor(afterReturningAdvice, pointcut);
        final Advisor rollbackAdvisor = new Advisor(throwsAdvice, pointcut);

        factoryBean.setTargetClass(new TypeTarget<>(bean.getClass()));
        factoryBean.addAdvisors(beginAdvisor, commitAdvisor, rollbackAdvisor);

        return factoryBean;
    }
}
