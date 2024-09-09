package com.interface21.beans.factory.proxy.advice;

import java.lang.reflect.Method;

public interface Invocation {
    Method getMethod();
    Object proceed() throws Throwable;
}
