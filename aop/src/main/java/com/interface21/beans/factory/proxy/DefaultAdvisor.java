package com.interface21.beans.factory.proxy;

import com.interface21.beans.factory.proxy.pointcut.Pointcut;
import net.sf.cglib.proxy.MethodInterceptor;

public class DefaultAdvisor implements Advisor {

    private final Pointcut pointcut;
    private final MethodInterceptor advice;

    public DefaultAdvisor(Pointcut pointcut, MethodInterceptor advice) {
        this.pointcut = pointcut;
        this.advice = advice;
    }

    @Override
    public MethodInterceptor getAdvice() {
        return this.advice;
    }

    public Pointcut getPointcut() {
        return pointcut;
    }
}
