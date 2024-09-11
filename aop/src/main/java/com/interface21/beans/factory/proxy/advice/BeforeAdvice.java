package com.interface21.beans.factory.proxy.advice;

import java.lang.reflect.Method;

public interface BeforeAdvice extends Advice {

    void before(Method method, Object[] args, Object target) throws Throwable;

}
