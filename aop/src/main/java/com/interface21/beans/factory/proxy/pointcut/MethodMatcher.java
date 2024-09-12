package com.interface21.beans.factory.proxy.pointcut;

import java.lang.reflect.Method;

public interface MethodMatcher extends Pointcut {

    boolean matches(Method method, Class<?> targetClass);

    boolean isRuntime();
}
