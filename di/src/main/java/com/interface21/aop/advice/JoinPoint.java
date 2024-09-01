package com.interface21.aop.advice;

import java.lang.reflect.Method;

public class JoinPoint {
    private final Object target;
    private final Method method;
    private final Object[] args;

    public JoinPoint(Object target, Method method, Object[] args) {
        this.target = target;
        this.method = method;
        this.args = args;
    }

    public Object proceed() throws Throwable {
        return method.invoke(target, args);
    }
}
