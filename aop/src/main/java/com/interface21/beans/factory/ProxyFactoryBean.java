package com.interface21.beans.factory;

import com.interface21.framework.Advised;
import com.interface21.framework.AopProxy;
import com.interface21.framework.AopProxyFactory;
import com.interface21.framework.DefaultAopProxyFactory;

public class ProxyFactoryBean<T> implements FactoryBean<T>{

    private T singletonInstance;
    private final Advised advised;

    private final AopProxyFactory aopProxyFactory;

    public ProxyFactoryBean(Advised advised) {
        this.advised = advised;
        this.aopProxyFactory = DefaultAopProxyFactory.getInstance();
    }

    @Override
    public T getObject() {
        if (singletonInstance == null) {
            AopProxy aopProxy = createAopProxy();
            singletonInstance = (T) aopProxy.getProxy();
        }
        return singletonInstance;
    }

    @Override
    public Class<?> getObjectType() {
        return advised.getTarget();
    }


    private AopProxy createAopProxy() {
        return aopProxyFactory.createAopProxy(advised);
    }
}
