package com.interface21.beans.factory.proxy;

import java.lang.reflect.Method;

public interface MethodBeforeAdvice extends Advice {
    void invoke(final Method method, final Object[] args, final Object target) throws Throwable;
}
