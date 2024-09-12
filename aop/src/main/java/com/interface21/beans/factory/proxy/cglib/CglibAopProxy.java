package com.interface21.beans.factory.proxy.cglib;

import com.interface21.beans.factory.Advised;
import com.interface21.beans.factory.proxy.advice.DynamicAdvisedInterceptor;
import com.interface21.framework.AopProxy;
import net.sf.cglib.proxy.Enhancer;

public class CglibAopProxy implements AopProxy {

    private final Advised advised;

    public CglibAopProxy(Advised advised) {
        this.advised = advised;
    }

    @Override
    public Object getProxy() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(this.advised.getTarget());
        enhancer.setCallback(new DynamicAdvisedInterceptor(this.advised));
        return enhancer.create();
    }


}
