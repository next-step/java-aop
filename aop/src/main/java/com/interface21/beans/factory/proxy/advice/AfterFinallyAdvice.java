package com.interface21.beans.factory.proxy.advice;

import net.sf.cglib.proxy.MethodInterceptor;

import java.lang.reflect.Method;

public interface AfterFinallyAdvice extends MethodInterceptor {
    void afterFinally(Method method, Object[] args, Object target) throws Throwable;

}
