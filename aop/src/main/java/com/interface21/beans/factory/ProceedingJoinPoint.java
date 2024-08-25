package com.interface21.beans.factory;

import java.lang.reflect.Method;

public class ProceedingJoinPoint implements JointPoint {

    private final Object target;
    private final Method method;
    private final Object[] args;

    public ProceedingJoinPoint(Object target, Method method, Object[] args) {
        this.target = target;
        this.method = method;
        this.args = args;
    }

    @Override
    public Object proceed() throws Throwable {
        return method.invoke(target, args);
    }

    @Override
    public Object[] getArgs() {
        return args;
    }

    @Override
    public Object getTarget() {
        return target;
    }

    @Override
    public String getSignature() {
        return method.getName();
    }
}
