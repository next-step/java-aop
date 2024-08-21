package com.interface21.beans.factory.proxy;

public interface Advice {

    Object invoke(JoinPoint joinPoint) throws Throwable;
}
