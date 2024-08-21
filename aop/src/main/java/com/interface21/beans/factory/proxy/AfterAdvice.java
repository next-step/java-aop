package com.interface21.beans.factory.proxy;

import java.lang.reflect.Method;

public abstract class AfterAdvice implements Advice {

    abstract void afterReturning(Object returnValue, Method method, Object[] args);

    @Override
    public final Object invoke(JoinPoint joinPoint) throws Throwable {
        Object result = joinPoint.proceed();
        afterReturning(result, joinPoint.getMethod(), joinPoint.getArguments());
        return result;
    }
}
