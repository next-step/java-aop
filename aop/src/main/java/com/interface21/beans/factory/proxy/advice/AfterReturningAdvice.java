package com.interface21.beans.factory.proxy.advice;

import net.sf.cglib.proxy.MethodInterceptor;

import java.lang.reflect.Method;

public interface AfterReturningAdvice extends MethodInterceptor {
    void afterReturning(Object returned, Method method, Object[] args, Object target);

}
