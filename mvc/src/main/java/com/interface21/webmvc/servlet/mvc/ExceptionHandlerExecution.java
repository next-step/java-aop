package com.interface21.webmvc.servlet.mvc;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ExceptionHandlerExecution {

    private final Object declaredObject;
    private final Method method;

    public ExceptionHandlerExecution(final Object declaredObject, final Method method) {
        this.declaredObject = declaredObject;
        this.method = method;
    }

    public Object handle(final Throwable throwable) {
        try {
            return method.invoke(declaredObject, throwable);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new IllegalStateException("cannot invoke ExceptionHandlerExecution", e);
        }
    }
}
