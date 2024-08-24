package com.interface21.webmvc.servlet.mvc.tobe.exception;

import com.interface21.webmvc.servlet.ModelAndView;

import java.lang.reflect.Method;

public class ExceptionHandlerExecution {

    private final Object declaredObject;
    private final Method method;

    public ExceptionHandlerExecution(Object declaredObject, Method method) {
        this.declaredObject = declaredObject;
        this.method = method;
    }

    public ModelAndView handle(Exception exception) throws Exception {
        return (ModelAndView) method.invoke(declaredObject, exception);
    }

    public Method getMethod() {
        return method;
    }
}
