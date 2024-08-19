package com.interface21.beans.factory.proxy;

import java.lang.reflect.Method;

public class TruePointcut implements Pointcut {
    public static final TruePointcut INSTANCE = new TruePointcut();

    @Override
    public boolean matches(final Method method, final Class<?> targetClass) {
        return true;
    }
}
