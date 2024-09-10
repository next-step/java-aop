package com.interface21.beans.factory.proxy.joinpoint;

import java.lang.reflect.Method;

public interface Invocation {

    Method getMethod();

    Object proceed() throws Throwable;
}
