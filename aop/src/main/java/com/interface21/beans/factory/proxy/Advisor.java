package com.interface21.beans.factory.proxy;

import com.interface21.beans.factory.proxy.advice.Interceptor;
import com.interface21.beans.factory.proxy.joinpoint.MethodInvocation;
import com.interface21.beans.factory.proxy.pointcut.Pointcut;

public interface Advisor {

    Pointcut getPointcut();
    Interceptor getAdvice();

    Object invoke(MethodInvocation methodInvocation) throws Throwable;
}
