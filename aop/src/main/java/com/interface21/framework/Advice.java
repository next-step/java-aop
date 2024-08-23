package com.interface21.framework;

public interface Advice {

    void invoke(MethodInvocation invocation) throws Throwable;
}
