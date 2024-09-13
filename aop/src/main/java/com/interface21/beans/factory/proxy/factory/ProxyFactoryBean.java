package com.interface21.beans.factory.proxy.factory;

import com.interface21.beans.factory.Advised;
import com.interface21.beans.factory.proxy.DefaultAopProxyFactory;
import com.interface21.beans.factory.proxy.FactoryBean;
import com.interface21.beans.factory.proxy.ProxyFactory;
import com.interface21.framework.AopProxy;

public class ProxyFactoryBean<T> implements FactoryBean<T> {

    private final Class<T> target;
    private final Advised adivsors;
    private final ProxyFactory proxyFactory;

    public ProxyFactoryBean(Class<T> target, Advised adivsors) {
        this.target = target;
        this.adivsors = adivsors;
        this.proxyFactory = new DefaultAopProxyFactory();
    }

    @Override
    public T getObject() {
        AopProxy aopProxy = proxyFactory.createAopProxy(adivsors);
        return (T) aopProxy.getProxy();
    }
}
