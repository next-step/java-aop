package com.interface21.beans.factory;

import com.interface21.aop.advice.AroundAdvice;
import com.interface21.aop.advisor.Advisor;
import com.interface21.aop.advisor.Pointcut;
import com.interface21.beans.factory.annotation.Autowired;
import com.interface21.dao.DataAccessException;
import com.interface21.transaction.PlatformTransactionManager;
import com.interface21.tx.annotation.Transactional;

import java.lang.reflect.Method;
import java.util.Arrays;

public class TransactionBeanPostProcessor implements BeanPostProcessor {

    private final BeanFactory beanFactory;

    @Autowired
    public TransactionBeanPostProcessor(final BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    @Override
    public Object postInitialization(Object bean) {
        Class<?> beanType = bean.getClass();

        if (!checkTransactional(beanType)) {
            return bean;
        }

        ProxyFactoryBean<Object> pfb = new ProxyFactoryBean<>();
        pfb.setTargetClass(beanType);
        pfb.setObjectType(beanType);
        pfb.addAdvisor(getAdvisor());

        System.out.println("TransactionBeanPostProcessor postInitialization: " + beanType.getName());
        return pfb;
    }

    private Advisor getAdvisor() {
        Pointcut pointcut = (method, targetClass) -> method.isAnnotationPresent(Transactional.class);
        PlatformTransactionManager transactionManager = beanFactory.getBean(PlatformTransactionManager.class);
        AroundAdvice advice = methodInvocation -> {
            transactionManager.begin();

            try {
                methodInvocation.proceed();
            } catch (Exception e) {
                transactionManager.rollback();
                throw new DataAccessException(e);
            }
            transactionManager.commit();
            return null;
        };

        return new Advisor(pointcut, advice);
    }

    private static boolean checkTransactional(Class<?> beanType) {
        if (beanType.isAnnotationPresent(Transactional.class)) {
            return true;
        }

        Method[] declaredMethods = beanType.getDeclaredMethods();
        return Arrays.stream(declaredMethods)
                .anyMatch(method -> method.isAnnotationPresent(Transactional.class));
    }
}
