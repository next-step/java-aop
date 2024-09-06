package com.interface21.aop.advisor;

import com.interface21.aop.advice.Advice;

public class Advisor {
    private final Pointcut pointcut;
    private final Advice advice;

    public Advisor(Advice advice) {
        this(null, advice);
    }

    public Advisor(Pointcut pointcut, Advice advice) {
        this.pointcut = pointcut;
        this.advice = advice;
    }

    public Advice getAdvice() {
        return advice;
    }

    public Pointcut getPointcut() {
        return pointcut;
    }
}
