package com.interface21.beans.factory.proxy.advice;

import com.interface21.beans.factory.proxy.joinpoint.Invocation;

public interface Advice {

    Object invoke(Invocation invocation) throws Throwable;
}
