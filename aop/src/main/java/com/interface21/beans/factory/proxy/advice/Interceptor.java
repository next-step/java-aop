package com.interface21.beans.factory.proxy.advice;

public interface Interceptor extends Advice {
    Object invoke(Invocation invocation);
}
