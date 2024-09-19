package com.interface21.transaction.bean;

import com.interface21.beans.factory.Advised;
import com.interface21.beans.factory.config.AdvisedSupport;
import com.interface21.beans.factory.proxy.Advisor;
import com.interface21.beans.factory.proxy.factory.ProxyFactoryBean;
import com.interface21.transaction.PlatformTransactionManager;
import com.interface21.transaction.TransactionalAdvisor;
import com.interface21.transaction.TransactionalMatcher;
import java.util.Arrays;


public class TransactionalPostProcessor implements BeanPostProcessor {

    private final PlatformTransactionManager platformTransactionManager;

    public TransactionalPostProcessor(final PlatformTransactionManager platformTransactionManager) {
        this.platformTransactionManager = platformTransactionManager;
    }

    @Override
    public Object postInitialization(Object bean) {
        Class<?> clazz = bean.getClass();
        Advised advisedSupport = new AdvisedSupport();
        Advisor advisor = new TransactionalAdvisor(platformTransactionManager);
        advisedSupport.addAdvisor(advisor);
        advisedSupport.setTarget(clazz);

        TransactionalMatcher matcher = (TransactionalMatcher) advisor.getPointcut();

        boolean transactional = Arrays.stream(clazz.getDeclaredMethods())
            .filter(method -> matcher.matches(method))
            .findAny()
            .isPresent();

        if (transactional) {
            return new ProxyFactoryBean<>(clazz,
                advisedSupport);
        }
        return bean;
    }
}
