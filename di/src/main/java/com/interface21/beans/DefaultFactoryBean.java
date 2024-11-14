package com.interface21.beans;

import com.interface21.beans.factory.FactoryBean;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;

public class DefaultFactoryBean implements FactoryBean {
    private Class<?> type;
    private MethodInterceptor methodInterceptor;

    public DefaultFactoryBean(
            Class<?> type,
            MethodInterceptor methodInterceptor
    ) {
        this.methodInterceptor = methodInterceptor;
        this.type = type;
    }

    @Override
    public Object getObject() throws Exception {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(type);
        enhancer.setCallback(methodInterceptor);
        return enhancer.create();
    }
}
