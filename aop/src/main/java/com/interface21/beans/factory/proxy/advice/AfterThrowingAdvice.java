package com.interface21.beans.factory.proxy.advice;

import net.sf.cglib.proxy.MethodInterceptor;

import java.lang.reflect.Method;

public interface AfterThrowingAdvice extends Interceptor {

    void afterThrowing(Method method, Object[] args, Object target);

}
