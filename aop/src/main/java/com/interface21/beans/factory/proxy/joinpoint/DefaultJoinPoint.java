package com.interface21.beans.factory.proxy.joinpoint;

import java.lang.reflect.Method;
import net.sf.cglib.proxy.MethodProxy;

public class DefaultJoinPoint implements MethodInvocation {

    private final MethodProxy proxy;
    private final Method method;
    private final Object[] args;
    private final Object target;

    public DefaultJoinPoint(MethodProxy proxy, Method method, Object[] args, Object target) {
        this.proxy = proxy;
        this.method = method;
        this.args = args;
        this.target = target;
    }

    @Override
    public Method getMethod() {
        return this.method;
    }

    @Override
    public Object proceed() throws Throwable {
        return proxy.invoke(target, args);
    }

    @Override
    public Object[] getArguments() {
        return args;
    }
}
