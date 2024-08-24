package com.interface21.framework;

import java.lang.reflect.Method;

public class Advisor {

    private final PointCut pointCut;
    private final Advice advice;

    public Advisor(PointCut pointCut, Advice advice) {
        this.pointCut = pointCut;
        this.advice = advice;
    }

    public void invoke(MethodInvocation invocation) throws Throwable {
        if (matches(invocation.getMethod())) {
            advice.invoke(invocation);
        }
    }

    public boolean isBeforeAdvice() {
        return advice instanceof BeforeAdvice;
    }

    public boolean isAfterAdvice() {
        return advice instanceof AfterAdvice;
    }

    public boolean isAfterReturningAdvice() {
        return advice instanceof AfterReturningAdvice;
    }

    public boolean isAfterThrowingAdvice() {
        return advice instanceof AfterThrowingAdvice;
    }

    private boolean matches(Method method) {
        return pointCut.matches(method);
    }
}
