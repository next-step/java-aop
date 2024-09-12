package com.interface21.beans.factory.proxy.advice;

import java.lang.reflect.Method;
import net.sf.cglib.proxy.MethodInterceptor;

public interface AfterFinallyAdvice extends MethodInterceptor {
    void afterFinally(Method method, Object[] args, Object target) throws Throwable;

}
