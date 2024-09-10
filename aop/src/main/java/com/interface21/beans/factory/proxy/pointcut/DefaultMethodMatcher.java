package com.interface21.beans.factory.proxy.pointcut;

import java.lang.reflect.Method;

public class DefaultMethodMatcher implements MethodMatcher {

    private final String methodName;

    public DefaultMethodMatcher(String methodName) {
        this.methodName = methodName;
    }

    @Override
    public boolean matches(Method method, Class<?> targetClass) {
        return method.getName().contains(this.methodName);
    }

    @Override
    public boolean isRuntime() {
        return false;
    }
}
