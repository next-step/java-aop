package com.interface21.framework;

public class CglibAopProxy implements AopProxy {

    private final Object proxy;

    public CglibAopProxy(final Object proxy) {
        this.proxy = proxy;
    }

    @Override
    public Object getProxy() {
        return proxy;
    }
}
