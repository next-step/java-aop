package com.interface21.beans.factory.proxy;

import com.interface21.beans.factory.FactoryBean;
import net.sf.cglib.proxy.Enhancer;

public class ProxyFactoryBean<T> implements FactoryBean<T> {

    private final Target<T> target;
    private final Advisor advisor;

    public ProxyFactoryBean(Target<T> target, Advisor advisor) {
        this.target = target;
        this.advisor = advisor;
    }

    @Override
    public T getObject() {
        return (T) Enhancer.create(target.getType(), advisor.createMethodInterceptor());
    }

    @Override
    public Class<T> getType() {
        return target.getType();
    }
}
