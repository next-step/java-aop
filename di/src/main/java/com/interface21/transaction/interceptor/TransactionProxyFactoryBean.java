package com.interface21.transaction.interceptor;

import com.interface21.beans.factory.FactoryBean;
import com.interface21.beans.factory.ProxyFactoryBean;
import com.interface21.transaction.PlatformTransactionManager;

public class TransactionProxyFactoryBean<T> implements FactoryBean<T> {

    private final ProxyFactoryBean<T> proxyFactoryBean;

    public TransactionProxyFactoryBean(final ProxyFactoryBean<T> proxyFactoryBean, final PlatformTransactionManager transactionManager) {
        this.proxyFactoryBean = proxyFactoryBean;
        proxyFactoryBean.addAdvisors(new TransactionAdvisor(transactionManager));
    }

    @Override
    public T getObject() {
        return proxyFactoryBean.getObject();
    }
}
