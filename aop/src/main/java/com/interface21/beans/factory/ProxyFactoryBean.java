package com.interface21.beans.factory;

import java.util.ArrayList;
import java.util.List;
import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;

public class ProxyFactoryBean implements FactoryBean<Object> {
    private final Class<?> targetClass;
    private final List<MethodInterceptor> methodInterceptors;

    public ProxyFactoryBean(final Class<?> targetClass) {
        this.targetClass = targetClass;
        this.methodInterceptors = new ArrayList<>();
    }

    @Override
    public Object getObject() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(targetClass);
        enhancer.setCallbacks(methodInterceptors.toArray(new Callback[0]));
        return enhancer.create();
    }

    public void addMethodInterceptor(final MethodInterceptor methodInterceptor) {
        methodInterceptors.add(methodInterceptor);
    }
}
