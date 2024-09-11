package com.interface21.beans.factory.proxy.advice;

import com.interface21.beans.factory.proxy.joinpoint.Invocation;

public class DefaultMethodInterceptor implements Interceptor {

    @Override
    public Object invoke(Invocation invocation) throws Throwable {
        return invocation.proceed();
    }
}
