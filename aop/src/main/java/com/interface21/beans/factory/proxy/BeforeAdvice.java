package com.interface21.beans.factory.proxy;

import java.lang.reflect.Method;

public abstract class BeforeAdvice implements Advice {

    abstract void before(Method method, Object[] args);

    @Override
    public final Object invoke(JoinPoint joinPoint) throws Throwable {
        before(joinPoint.getMethod(), joinPoint.getArguments());
        return joinPoint.proceed();
    }
}
