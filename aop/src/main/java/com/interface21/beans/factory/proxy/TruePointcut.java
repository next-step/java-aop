package com.interface21.beans.factory.proxy;

import java.lang.reflect.Method;

public class TruePointcut implements Pointcut {
    private static final TruePointcut INSTANCE = new TruePointcut();

    private TruePointcut() {
    }

    public static TruePointcut getInstance() {
        return INSTANCE;
    }

    @Override
    public boolean matches(final Method method, final Class<?> targetClass) {
        return true;
    }
}
