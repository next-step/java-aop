package com.interface21.beans.factory.proxy;

import java.lang.reflect.Method;

public interface JoinPoint {

    Object proceed() throws Throwable;

    Method getMethod();

    Object[] getArguments();
}
