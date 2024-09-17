package com.interface21.beans.factory.proxy.advice;

import com.interface21.beans.factory.Advised;
import com.interface21.beans.factory.proxy.Advisor;
import com.interface21.beans.factory.proxy.joinpoint.DefaultJoinPoint;
import com.interface21.beans.factory.proxy.joinpoint.MethodInvocation;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.List;

public class DynamicAdvisedInterceptor implements MethodInterceptor {
    private final Advised advised;

    public DynamicAdvisedInterceptor(Advised advised) {
        this.advised = advised;
    }

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {

        List<Interceptor> interceptors = advised.getAdvisors().stream()
            .map(Advisor::getAdvice)
            .toList();
        MethodInvocation invocation = new DefaultJoinPoint(proxy, method, args, obj, interceptors);

        for (Advisor advisor : advised.getAdvisors()) {
            if (advisor.getAdvice() instanceof BeforeAdvice) {
                ((BeforeAdvice) advisor.getAdvice()).before(method, args, advised.getTarget());

            }
        }

        Object returnValue = invocation.proceed();

        for (Advisor advisor : advised.getAdvisors()) {
            if (advisor.getAdvice() instanceof AfterReturningAdvice) {
                ((AfterReturningAdvice) advisor.getAdvice()).afterReturning(returnValue, method, args, advised.getTarget());
            }
        }

        return returnValue;
    }
}
