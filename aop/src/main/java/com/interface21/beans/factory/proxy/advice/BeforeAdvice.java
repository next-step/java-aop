package com.interface21.beans.factory.proxy.advice;

import java.lang.reflect.Method;
import net.sf.cglib.proxy.MethodInterceptor;

public interface BeforeAdvice extends MethodInterceptor {

    void before(Method method, Object[] args, Object target) throws Throwable;

}
