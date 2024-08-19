package com.interface21.beans.factory.proxy;

import java.lang.reflect.Method;

public interface AfterReturningAdvice extends Advice {
    void afterReturning(final Object returnValue, final Method method, final Object[] args, final Object target) throws Throwable;
}
