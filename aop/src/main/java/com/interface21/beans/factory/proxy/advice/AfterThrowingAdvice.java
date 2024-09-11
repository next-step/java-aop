package com.interface21.beans.factory.proxy.advice;

import java.lang.reflect.Method;

public interface AfterThrowingAdvice extends Advice {

    void afterThrowing(Method method, Object[] args, Object target);

}
