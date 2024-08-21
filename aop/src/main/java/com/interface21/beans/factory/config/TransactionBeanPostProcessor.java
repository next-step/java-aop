package com.interface21.beans.factory.config;

import com.interface21.beans.factory.FactoryBean;
import com.interface21.beans.factory.proxy.Advisor;
import com.interface21.beans.factory.proxy.ProxyFactoryBean;
import com.interface21.beans.factory.proxy.Target;
import com.interface21.transaction.PlatformTransactionManager;
import com.interface21.transaction.aop.TransactionAdvice;
import com.interface21.transaction.aop.TransactionalPointCut;

public class TransactionBeanPostProcessor implements BeanPostProcessor {

    private final PlatformTransactionManager transactionManager;

    public TransactionBeanPostProcessor(PlatformTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    @Override
    public Object postInitialization(Object bean) {
        Advisor advisor = new Advisor(new TransactionalPointCut(), new TransactionAdvice(transactionManager));
        FactoryBean<?> factoryBean = new ProxyFactoryBean<>(new Target<>(bean.getClass()), advisor);
        return factoryBean.getObject();
    }
}
