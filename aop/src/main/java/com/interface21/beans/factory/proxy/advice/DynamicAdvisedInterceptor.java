package com.interface21.beans.factory.proxy.advice;

import com.interface21.beans.factory.Advised;
import com.interface21.beans.factory.proxy.Advisor;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class DynamicAdvisedInterceptor implements MethodInterceptor {
    private final Advised advised;

    public DynamicAdvisedInterceptor(Advised advised) {
        this.advised = advised;
    }

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        for (Advisor advisor : advised.getAdvisors()) {
            if (advisor.getAdvice() instanceof BeforeAdvice) {
                ((BeforeAdvice) advisor.getAdvice()).before(method, args, advised.getTarget());
                advisor.getAdvice().intercept(obj, method, args, proxy);

            }
        }

        Object returnValue = proxy.invokeSuper(obj, args);

        for (Advisor advisor : advised.getAdvisors()) {
            if (advisor.getAdvice() instanceof AfterReturningAdvice) {
                ((AfterReturningAdvice) advisor.getAdvice()).afterReturning(returnValue, method, args, advised.getTarget());
                advisor.getAdvice().intercept(obj, method, args, proxy);
            }
        }

        return returnValue;
    }
}
