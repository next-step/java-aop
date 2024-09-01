package com.interface21.aop.advice;

public interface AroundAdvice extends Advice {
    Object invoke(MethodInvocation methodInvocation) throws Throwable;
}
