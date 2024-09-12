package com.interface21.beans.factory.proxy;

import com.interface21.beans.factory.proxy.advice.Interceptor;
import com.interface21.beans.factory.proxy.joinpoint.MethodInvocation;

public interface Advisor {

    Interceptor getAdvice();

    Object invoke(MethodInvocation methodInvocation) throws Throwable;
}
