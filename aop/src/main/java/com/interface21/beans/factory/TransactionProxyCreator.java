package com.interface21.beans.factory;

import com.interface21.beans.factory.config.BeanPostProcessor;
import com.interface21.transaction.PlatformTransactionManager;
import com.interface21.transaction.TransactionMethodInterceptor;
import com.interface21.transaction.support.DataSourceTransactionManager;
import javax.sql.DataSource;

public class TransactionProxyCreator implements BeanPostProcessor {
    private final PlatformTransactionManager transactionManager;

    public TransactionProxyCreator(final DataSource dataSource) {
        this.transactionManager = new DataSourceTransactionManager(dataSource);
    }

    @Override
    public Object postInitialization(final Object bean) {
        ProxyFactoryBean proxyFactoryBean = new ProxyFactoryBean(bean.getClass());
        proxyFactoryBean.addMethodInterceptor(new TransactionMethodInterceptor(transactionManager, bean));
        return proxyFactoryBean.getObject();
    }
}
