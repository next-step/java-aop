package com.interface21.beans.factory.proxy.advice;

import java.lang.reflect.Method;

public interface AfterFinallyAdvice extends Advice {
    void afterFinally(Method method, Object[] args, Object target) throws Throwable;

}
