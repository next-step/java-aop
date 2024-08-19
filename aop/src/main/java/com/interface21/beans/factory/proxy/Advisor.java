package com.interface21.beans.factory.proxy;

public class Advisor {
    private final Advice advice;
    private final Pointcut pointcut;

    public Advisor(final Advice advice) {
        this.advice = advice;
        this.pointcut = Pointcut.TRUE;
    }

    public Advisor(final Advice advice, final Pointcut pointcut) {
        this.advice = advice;
        this.pointcut = pointcut;
    }

    public Pointcut getPointcut() {
        return this.pointcut;
    }

    public Advice getAdvice() {
        return this.advice;
    }
}
