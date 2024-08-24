package com.interface21.beans.factory;

import java.lang.reflect.Method;

public class TrueMethodMatcher implements MethodMatcher {

    public static final TrueMethodMatcher INSTANCE = new TrueMethodMatcher();

    private TrueMethodMatcher() {
    }

    @Override
    public boolean matches(final Method method, final Class<?> targetClass, final Object... args) {
        return true;
    }
}
