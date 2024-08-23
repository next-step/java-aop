package com.interface21.framework;

import java.lang.reflect.Method;
import net.sf.cglib.proxy.MethodProxy;

public class CglibMethodInvocation implements MethodInvocation {

    private final MethodProxy proxy;
    private final Target target;
    private final Method method;
    private final Object[] arguments;
    private Object returnValue;

    public CglibMethodInvocation(MethodProxy proxy, Target target, Method method, Object[] args) {
        this.proxy = proxy;
        this.target = target;
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
    public Target getTarget() {
        return target;
    }

    @Override
    public Object proceed() throws Throwable {
        Object result = proxy.invokeSuper(target.getTarget(), arguments);
        returnValue = result;
        return result;
    }
}
