package com.interface21.aop;

import com.interface21.aop.advisor.Advisor;
import com.interface21.beans.factory.FactoryBean;

import java.util.ArrayList;
import java.util.List;

public class ProxyFactoryBean<T> implements FactoryBean<T> {
    private T target = null;
    private Class<?>[] interfaces;
    private Class<T> objectType;
    private final List<Advisor> advisors = new ArrayList<>();

    public void setTarget(T target) {
        this.target = target;
    }

    public void setInterfaces(Class<?>[] interfaces) {
        this.interfaces = interfaces;
    }

    public void setObjectType(Class<T> objectType) {
        this.objectType = objectType;
    }

    public void addAdvisor(Advisor advisor) {
        advisors.add(advisor);
    }

    @SuppressWarnings("unchecked")
    @Override
    public T getObject() {
        ProxyFactory proxyFactory = new ProxyFactory(target, interfaces);

        advisors.forEach(proxyFactory::addAdvisor);
        return (T) proxyFactory.getProxy();
    }

    @Override
    public Class<T> getObjectType() {
        return objectType;
    }
}
