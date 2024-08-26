package com.interface21.beans.factory;

import java.lang.reflect.Method;

@FunctionalInterface
public interface MethodMatcher {
    boolean matches(Method m, Class<?> targetClass, Object[] args);
}
