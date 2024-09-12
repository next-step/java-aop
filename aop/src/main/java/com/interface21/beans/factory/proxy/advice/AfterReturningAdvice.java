package com.interface21.beans.factory.proxy.advice;

import java.lang.reflect.Method;
import net.sf.cglib.proxy.MethodInterceptor;

public interface AfterReturningAdvice extends MethodInterceptor {
    void afterReturning(Object returned, Method method, Object[] args, Object target);

}
