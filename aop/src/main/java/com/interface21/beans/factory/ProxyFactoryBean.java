package com.interface21.beans.factory;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.NoOp;

public class ProxyFactoryBean<T> implements FactoryBean<T> {

    private final Class<T> clazz;

    public ProxyFactoryBean(final Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T getObject() {
        final Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(clazz);
        enhancer.setCallback(new NoOp() {
        });
        return (T) enhancer.create();
    }
}
