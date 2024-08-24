package com.interface21.beans.factory;

import java.lang.reflect.Method;

public interface MethodMatcher {

    MethodMatcher TRUE = TrueMethodMatcher.INSTANCE;

    boolean matches(Method method, Class<?> targetClass, Object... args);

}
