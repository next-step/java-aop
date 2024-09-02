package com.interface21.beans.factory;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;

public class ProxyFactoryBean<T> implements FactoryBean<T> {

    private final Class<T> target;
    private final MethodInterceptor advice;

    public ProxyFactoryBean(Class<T> target, MethodInterceptor advice) {
        this.target = target;
        this.advice = advice;
    }

    @Override
    public T getObject() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(this.target);
        enhancer.setCallback(advice);
        return (T) enhancer.create();
    }
}
