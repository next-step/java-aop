package com.interface21.beans.factory.proxy.pointcut;

import java.lang.reflect.Method;

public interface MethodMatcher {
    boolean matches(Method method, Class<?> targetClass);
    boolean isRuntime();
}
