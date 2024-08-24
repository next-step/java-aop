package com.interface21.webmvc.servlet.mvc.tobe.exception;

public class ExceptionHandlerKey {

    private final Class<? extends Exception> targetExceptionType;

    public ExceptionHandlerKey(Class<? extends Exception> targetExceptionType) {
        this.targetExceptionType = targetExceptionType;
    }

    public boolean isMatch(Exception exception) {
        return targetExceptionType.isInstance(exception);
    }
}
