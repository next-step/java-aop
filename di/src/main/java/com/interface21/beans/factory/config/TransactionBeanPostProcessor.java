package com.interface21.beans.factory.config;

import com.interface21.beans.factory.BeanFactory;
import com.interface21.beans.factory.FactoryBean;
import com.interface21.beans.factory.proxy.Advisor;
import com.interface21.beans.factory.proxy.ProxyFactoryBean;
import com.interface21.beans.factory.proxy.Target;
import com.interface21.beans.factory.support.BeanFactoryUtils;
import com.interface21.transaction.PlatformTransactionManager;
import com.interface21.transaction.aop.TransactionAdvice;
import com.interface21.transaction.aop.TransactionalPointCut;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Arrays;

public class TransactionBeanPostProcessor implements BeanPostProcessor {

    private final Advisor transactionAdvisor;
    private final BeanFactory beanFactory;

    public TransactionBeanPostProcessor(BeanFactory beanFactory) {
        this.transactionAdvisor = new Advisor(
                new TransactionalPointCut(),
                new TransactionAdvice(beanFactory.getBean(PlatformTransactionManager.class))
        );
        this.beanFactory = beanFactory;
    }

    @Override
    public boolean accept(Object bean) {
        return Arrays.stream(getMethods(bean))
                .anyMatch(transactionAdvisor::matches);
    }

    private static Method[] getMethods(Object bean) {
        return bean.getClass()
                .getMethods();
    }

    @Override
    public Object postInitialization(Object bean) {
        Constructor<?> injectedConstructor = BeanFactoryUtils.getInjectedConstructor(bean.getClass());
        FactoryBean<?> factoryBean = new ProxyFactoryBean<>(
                new Target<>(bean, injectedConstructor),
                transactionAdvisor,
                beanFactory
        );
        return factoryBean.getObject();
    }
}
