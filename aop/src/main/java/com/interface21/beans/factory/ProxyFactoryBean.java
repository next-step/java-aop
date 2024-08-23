package com.interface21.beans.factory;

import com.interface21.framework.ProxyFactory;

public class ProxyFactoryBean<T> implements FactoryBean {

    private Class<T> target;
    private Advice advice;
    private PointCut pointCut;

    public void setTarget(Class<T> target) {
        this.target = target;
    }

    public void setAdvice(Advice advice) {
        this.advice = advice;
    }

    public void setPointCut(PointCut pointCut) {
        this.pointCut = pointCut;
    }

    @Override
    public T getObject() {
        ProxyFactory<T> proxyFactory = new ProxyFactory<>(target, advice, pointCut);
        return proxyFactory.getProxy();
    }
}
