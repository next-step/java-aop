package com.interface21.transaction;

import com.interface21.beans.factory.Advised;
import com.interface21.beans.factory.config.AdvisedSupport;
import com.interface21.beans.factory.config.BeanPostProcessor;
import com.interface21.beans.factory.proxy.factory.ProxyFactoryBean;

public class TransactionalPostProcessor implements BeanPostProcessor {

    private final PlatformTransactionManager platformTransactionManager;

    public TransactionalPostProcessor(final PlatformTransactionManager platformTransactionManager) {
        this.platformTransactionManager = platformTransactionManager;
    }

    @Override
    public Object postInitialization(Object bean) {
        Class<?> clazz = bean.getClass();
        Advised advisedSupport = new AdvisedSupport();
        advisedSupport.addAdvisor(new TransactionalAdvisor(platformTransactionManager));
        advisedSupport.setTarget(clazz);

       return new ProxyFactoryBean<>(clazz,
            advisedSupport);
    }
}
