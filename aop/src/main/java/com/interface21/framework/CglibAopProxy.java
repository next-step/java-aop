package com.interface21.framework;

import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.Enhancer;

public class CglibAopProxy implements AopProxy {

    private final Advised advised;

    public CglibAopProxy(Advised advised) {
        this.advised = advised;
    }
    @Override
    public Object getProxy() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(advised.getTarget());
        enhancer.setCallbacks(getCallbacks());

        return enhancer.create();
    }

    private Callback[] getCallbacks() {
        return new Callback[] {
            new CglibMethodInterceptor(advised)
        };
    }
}
