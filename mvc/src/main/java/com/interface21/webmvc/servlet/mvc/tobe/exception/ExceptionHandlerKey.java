package com.interface21.webmvc.servlet.mvc.tobe.exception;

import java.util.Objects;

public class ExceptionHandlerKey {

    private final Class<? extends Exception> targetExceptionType;

    public ExceptionHandlerKey(Class<? extends Exception> targetExceptionType) {
        this.targetExceptionType = targetExceptionType;
    }

    public ExceptionHandlerKey(Exception exception) {
        this(exception.getClass());
    }

    public boolean isMatch(Exception exception) {
        return targetExceptionType.isInstance(exception);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExceptionHandlerKey that = (ExceptionHandlerKey) o;
        return Objects.equals(targetExceptionType, that.targetExceptionType);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(targetExceptionType);
    }
}
