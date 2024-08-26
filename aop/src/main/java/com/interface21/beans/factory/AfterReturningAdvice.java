package com.interface21.beans.factory;

import java.lang.reflect.Method;

public interface AfterReturningAdvice extends AfterAdvice {

    void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable;

}
