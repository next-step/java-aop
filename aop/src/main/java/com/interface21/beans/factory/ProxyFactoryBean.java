package com.interface21.beans.factory;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;

public class ProxyFactoryBean<T> implements FactoryBean {

    private Class<T> target;
    private Advice advice;
    private PointCut pointCut;

    public ProxyFactoryBean(Class<T> target, Advice advice, PointCut pointCut) {
        this.target = target;
        this.advice = advice;
        this.pointCut = pointCut;
    }

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
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(target);
        enhancer.setCallback(methodInterceptor());
        return (T) enhancer.create();
    }

    private MethodInterceptor methodInterceptor() {
        return (object, method, args, methodProxy) -> {
            Object result;
            if (pointCut.matches(method)) {
                result = advice.around(new ProceedingJoinPoint(object, method, args));
            } else {
                result = methodProxy.invokeSuper(object, args);
            }
            return result;
        };
    }
}
