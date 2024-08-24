package com.interface21.framework;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;

public class CglibAopProxy implements AopProxy {

    private final Object proxy;

    public CglibAopProxy(final Class<?> targetClass, final MethodInterceptor methodInterceptor) {
        this.proxy = Enhancer.create(targetClass, methodInterceptor);
    }

    @Override
    public Object getProxy() {
        return proxy;
    }
}
