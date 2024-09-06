package com.interface21.aop.advisor;

import java.lang.reflect.Method;

public interface Pointcut {
    boolean matches(final Method method, final Class<?> targetClass);
}
