package com.interface21.beans.factory;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;

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
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(target);
        enhancer.setCallback(methodInterceptor());
        return (T) enhancer.create();
    }

    private MethodInterceptor methodInterceptor() {
        return (object, method, args, methodProxy) -> {
            if (pointCut.matches(method)) {
                advice.before();
            }

            Object result = methodProxy.invokeSuper(object, args);

            if (pointCut.matches(method)) {
                advice.after();
            }
            return result;
        };
    }
}
