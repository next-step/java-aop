package com.interface21.aop;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;

public class ProxyFactory {
    private final Class<?> targetClass;
    private final MethodInterceptor methodInterceptor;

    public ProxyFactory(Class<?> targetClass, MethodInterceptor methodInterceptor) {
        this.targetClass = targetClass;
        this.methodInterceptor = methodInterceptor;
    }

    public Object getProxy() {
        final var enhancer = new Enhancer();
        enhancer.setSuperclass(targetClass);
        enhancer.setCallback(methodInterceptor);
        return enhancer.create();
    }
}
