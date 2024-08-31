package com.interface21.aop.advice;

public interface AroundAdvice extends Advice {
    Object invoke(JoinPoint joinPoint) throws Throwable;
}
