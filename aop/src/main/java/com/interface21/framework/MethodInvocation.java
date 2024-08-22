package com.interface21.framework;

import java.lang.reflect.Method;

public interface MethodInvocation extends JoinPoint {

    Method getMethod();

    Object[] getArguments();

    Target getTarget();

    Object getReturnValue();

    void setReturnValue(Object returnValue);
}
