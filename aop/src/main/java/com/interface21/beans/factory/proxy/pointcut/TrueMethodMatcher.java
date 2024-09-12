package com.interface21.beans.factory.proxy.pointcut;

import java.lang.reflect.Method;

public class TrueMethodMatcher implements MethodMatcher {

    @Override
    public boolean matches(Method method, Class<?> targetClass) {
        return true;
    }

    @Override
    public boolean isRuntime() {
        return false;
    }

}
