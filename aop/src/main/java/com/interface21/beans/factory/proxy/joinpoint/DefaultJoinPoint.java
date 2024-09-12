package com.interface21.beans.factory.proxy.joinpoint;

import com.interface21.beans.factory.proxy.advice.Interceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.List;

public class DefaultJoinPoint implements MethodInvocation {

    private final MethodProxy proxy;
    private final Method method;
    private final Object[] args;
    private final Object target;
    private final List<Interceptor> invocations;
    private int currentInterceptorIndex = -1;

    public DefaultJoinPoint(MethodProxy proxy, Method method, Object[] args, Object target, List<Interceptor> invocations) {
        this.proxy = proxy;
        this.method = method;
        this.args = args;
        this.target = target;
        this.invocations = invocations;
    }

    @Override
    public Method getMethod() {
        return this.method;
    }

    @Override
    public Object proceed() throws Throwable {
        if (this.currentInterceptorIndex == this.invocations.size() - 1) {
            return proxy.invokeSuper(target, args);
        }

        Interceptor interceptorOrInterceptionAdvice = this.invocations.get(++this.currentInterceptorIndex);
        if (interceptorOrInterceptionAdvice instanceof Interceptor) {
            Interceptor mi = interceptorOrInterceptionAdvice;
            return mi.invoke(this);
        } else {
            return proceed();
        }
    }

    @Override
    public Object[] getArguments() {
        return args;
    }
}
