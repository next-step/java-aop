package com.interface21.framework;

import java.lang.reflect.Method;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class CglibMethodInterceptor implements MethodInterceptor {

    private final Advised advised;

    public CglibMethodInterceptor(Advised advised) {
        this.advised = advised;
    }

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        if (advised.getAdvisors().isEmpty()) {
            return proxy.invokeSuper(obj, args);
        }

        CglibMethodInvocation invocation = new CglibMethodInvocation(proxy, new ObjectTarget(obj), method, args);
        try {
            advised.before(invocation);
            invocation.proceed();
            advised.after(invocation);
            advised.afterReturning(invocation);
        } catch (Throwable ex) {
            // 필요시 advised.afterThrowing(invocation, ex) 구현
            throw ex;
        }

        return invocation.getReturnValue();
    }
}
