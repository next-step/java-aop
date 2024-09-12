package com.interface21.beans.factory.proxy;

import net.sf.cglib.proxy.MethodInterceptor;

public interface Advisor {

    MethodInterceptor getAdvice();
}
