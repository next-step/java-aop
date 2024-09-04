package com.interface21.beans.factory;

import com.interface21.aop.advisor.Advisor;
import com.interface21.beans.factory.annotation.Autowired;
import com.interface21.transaction.PlatformTransactionManager;
import com.interface21.tx.annotation.Transactional;
import com.interface21.tx.aop.MyAroundAdvice;
import com.interface21.tx.aop.TransactionalPointcut;

import java.lang.reflect.Method;
import java.util.Arrays;

public class TransactionBeanPostProcessor implements BeanPostProcessor {

    private final BeanFactory beanFactory;

    @Autowired
    public TransactionBeanPostProcessor(final BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Object postInitialization(Object bean) {
        Class<Object> beanType = (Class<Object>) bean.getClass();

        if (!isTransactionalAnnotationPresent(beanType)) {
            return bean;
        }

        return new ProxyFactoryBean<>(beanType, beanType, createAdvisor());
    }

    private Advisor createAdvisor() {
        PlatformTransactionManager transactionManager = beanFactory.getBean(PlatformTransactionManager.class);

        return new Advisor(new TransactionalPointcut(), new MyAroundAdvice(transactionManager));
    }

    private boolean isTransactionalAnnotationPresent(Class<?> beanType) {
        if (beanType.isAnnotationPresent(Transactional.class)) {
            return true;
        }

        Method[] declaredMethods = beanType.getDeclaredMethods();
        return Arrays.stream(declaredMethods)
                .anyMatch(method -> method.isAnnotationPresent(Transactional.class));
    }
}
