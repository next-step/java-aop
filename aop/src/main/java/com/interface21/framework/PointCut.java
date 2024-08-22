package com.interface21.framework;

import java.lang.reflect.Method;

@FunctionalInterface
public interface PointCut {

    boolean matches(Method method);
}
