package com.interface21.aop.advice;

import net.sf.cglib.proxy.MethodProxy;

public class MethodInvocation {
    private final Object obj;
    private final Object[] args;
    private final MethodProxy methodProxy;

    public MethodInvocation(Object obj, Object[] args, MethodProxy methodProxy) {
        this.obj = obj;
        this.args = args;
        this.methodProxy = methodProxy;
    }

    public Object proceed() throws Throwable {
        return methodProxy.invokeSuper(obj, args);
    }
}
