package com.interface21.beans.factory.proxy;

import com.interface21.beans.factory.proxy.advice.Interceptor;
import com.interface21.beans.factory.proxy.joinpoint.MethodInvocation;
import com.interface21.beans.factory.proxy.pointcut.Pointcut;

public class DefaultAdvisor implements Advisor {

    private final Pointcut pointcut;
    private final Interceptor advice;

    public DefaultAdvisor(Pointcut pointcut, Interceptor advice) {
        this.pointcut = pointcut;
        this.advice = advice;
    }

    @Override
    public Interceptor getAdvice() {
        return this.advice;
    }

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        return advice.invoke(methodInvocation);
    }

    public Pointcut getPointcut() {
        return pointcut;
    }
}
