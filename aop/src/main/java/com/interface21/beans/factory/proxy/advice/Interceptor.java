package com.interface21.beans.factory.proxy.advice;

import com.interface21.beans.factory.proxy.joinpoint.MethodInvocation;

public interface Interceptor {
    Object invoke(MethodInvocation invocation) throws Throwable;
}
