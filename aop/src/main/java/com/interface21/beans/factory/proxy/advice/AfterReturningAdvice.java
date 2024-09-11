package com.interface21.beans.factory.proxy.advice;

import java.lang.reflect.Method;

public interface AfterReturningAdvice extends Advice {
    void afterReturning(Object returned, Method method, Object[] args, Object target);

}
