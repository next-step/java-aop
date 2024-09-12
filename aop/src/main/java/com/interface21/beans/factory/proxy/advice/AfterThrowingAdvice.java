package com.interface21.beans.factory.proxy.advice;

import java.lang.reflect.Method;
import net.sf.cglib.proxy.MethodInterceptor;

public interface AfterThrowingAdvice extends MethodInterceptor {

    void afterThrowing(Method method, Object[] args, Object target);

}
