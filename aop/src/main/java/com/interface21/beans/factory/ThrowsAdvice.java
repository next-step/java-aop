package com.interface21.beans.factory;

import java.lang.reflect.Method;

public interface ThrowsAdvice extends AfterAdvice {
    void afterThrowing(Exception ex, Method method, Object[] args, Object target);
}
