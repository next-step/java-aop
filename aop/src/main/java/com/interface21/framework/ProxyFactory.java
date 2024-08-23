package com.interface21.framework;

import com.interface21.beans.factory.Advice;
import com.interface21.beans.factory.PointCut;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;

public class ProxyFactory<T> implements AopProxy {

    private Class<T> targetClass;
    private Advice advice;
    private PointCut pointCut;

    public ProxyFactory(Class<T> targetClass, Advice advice, PointCut pointCut) {
        this.targetClass = targetClass;
        this.advice = advice;
        this.pointCut = pointCut;
    }

    @SuppressWarnings("unchecked")
    public T getProxy() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(targetClass);
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
