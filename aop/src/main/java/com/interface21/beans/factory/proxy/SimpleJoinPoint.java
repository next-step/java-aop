package com.interface21.beans.factory.proxy;

import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class SimpleJoinPoint implements JoinPoint {

    private final Object target;
    private final Method method;
    private final Object[] arguments;
    private final MethodProxy proxy;

    public SimpleJoinPoint(Object target, Method method, Object[] arguments, MethodProxy proxy) {
        this.target = target;
        this.method = method;
        this.arguments = arguments;
        this.proxy = proxy;
    }

    @Override
    public Object proceed() throws Throwable {
        return proxy.invokeSuper(target, arguments);
    }

    @Override
    public Method getMethod() {
        return method;
    }

    @Override
    public Object[] getArguments() {
        return arguments;
    }
}
