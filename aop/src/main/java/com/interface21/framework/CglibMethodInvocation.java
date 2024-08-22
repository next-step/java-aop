package com.interface21.framework;

import java.lang.reflect.Method;
import net.sf.cglib.proxy.MethodProxy;

public class CglibMethodInvocation implements MethodInvocation {

    private final MethodProxy proxy;
    private final Object target;
    private final Method method;
    private final Object[] arguments;
    private Object returnValue;

    public CglibMethodInvocation(MethodProxy proxy, Object obj, Method method, Object[] args) {
        this.proxy = proxy;
        this.target = obj;
        this.method = method;
        this.arguments = args;
    }

    @Override
    public Method getMethod() {
        return method;
    }

    @Override
    public Object[] getArguments() {
        return arguments;
    }

    @Override
    public Object getReturnValue() {
        return returnValue;
    }

    @Override
    public void setReturnValue(Object returnValue) {
        this.returnValue = returnValue;
    }

    @Override
    public Object getTarget() {
        return target;
    }

    @Override
    public Object proceed() throws Throwable {
        Object result = proxy.invokeSuper(target, arguments);
        returnValue = result;
        return result;
    }
}
