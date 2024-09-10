package com.interface21.beans.factory.proxy;

import com.interface21.beans.factory.proxy.advice.Advice;
import com.interface21.beans.factory.proxy.pointcut.Pointcut;

public class DefaultAdvisor implements Advisor {

    private final Pointcut pointcut;
    private final Advice advice;

    public DefaultAdvisor(Pointcut pointcut, Advice advice) {
        this.pointcut = pointcut;
        this.advice = advice;
    }

    @Override
    public Advice getAdvice() {
        return this.advice;
    }

    public Pointcut getPointcut() {
        return pointcut;
    }
}
