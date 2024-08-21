package com.interface21.beans.factory.proxy;

import java.lang.reflect.Method;

@FunctionalInterface
public interface PointCut {

    boolean matches(Method method);
}
