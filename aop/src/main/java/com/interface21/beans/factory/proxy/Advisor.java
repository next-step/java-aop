package com.interface21.beans.factory.proxy;

import net.sf.cglib.proxy.MethodInterceptor;

public class Advisor {

    private final PointCut pointCut;
    private final Advice advice;

    public Advisor(PointCut pointCut, Advice advice) {
        this.pointCut = pointCut;
        this.advice = advice;
    }

    public MethodInterceptor createMethodInterceptor() {
        return (obj, method, args, proxy) -> {
            if (pointCut.matches(method)) {
                return advice.invoke(() -> proxy.invokeSuper(obj, args));
            }
            return proxy.invokeSuper(obj, args);
        };
    }
}
