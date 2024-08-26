package com.interface21.framework;

import com.interface21.beans.factory.proxy.*;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;

import java.lang.reflect.Method;
import java.util.List;

public class CglibAopProxy implements AopProxy {

    private final Object proxy;

    public CglibAopProxy(final Class<?> targetClass, final List<Advisor> advisors) {
        this.proxy = createProxy(targetClass, advisors);
    }

    public CglibAopProxy(final Class<?> targetClass, final List<Advisor> advisors, final Class<?>[] parameterTypes, final Object... params) {
        this.proxy = createProxy(targetClass, advisors, parameterTypes, params);
    }

    @Override
    public Object getProxy() {
        return proxy;
    }

    private Object createProxy(final Class<?> targetClass, final List<Advisor> advisors) {
        final Enhancer enhancer = createEnhancer(targetClass, advisors);
        return enhancer.create();
    }

    private Object createProxy(final Class<?> targetClass, final List<Advisor> advisors, final Class<?>[] parameterTypes, final Object... params) {
        final Enhancer enhancer = createEnhancer(targetClass, advisors);
        return enhancer.create(parameterTypes, params);
    }

    private Enhancer createEnhancer(final Class<?> targetClass, final List<Advisor> advisors) {
        final Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(targetClass);
        enhancer.setCallback(createMethodInterceptor(targetClass, advisors));
        return enhancer;
    }

    private MethodInterceptor createMethodInterceptor(final Class<?> targetClass, final List<Advisor> advisors) {
        return (o, method, objects, methodProxy) -> {
            final List<Advice> matchedAdvices = advisors.stream().filter(advisor -> advisor.getPointcut().matches(method, targetClass)).map(Advisor::getAdvice).toList();

            invokeMethodBefore(o, method, objects, matchedAdvices);

            final Object result;

            try {
                result = methodProxy.invokeSuper(o, objects);
            } catch (Exception e) {
                invokeThrows(matchedAdvices, e);
                throw e;
            }

            invokeAfterRunning(o, method, objects, matchedAdvices, result);

            return result;
        };
    }

    private void invokeMethodBefore(final Object o, final Method method, final Object[] objects, final List<Advice> matchedAdvices) {
        matchedAdvices.stream()
                .filter(advice -> advice instanceof MethodBeforeAdvice).toList()
                .forEach(advice -> invokeAdvice(() -> ((MethodBeforeAdvice) advice).invoke(method, objects, o)));
    }

    private void invokeAfterRunning(final Object o, final Method method, final Object[] objects, final List<Advice> matchedAdvices, final Object result) {
        matchedAdvices.stream()
                .filter(advice -> advice instanceof AfterReturningAdvice).toList()
                .forEach(advice -> invokeAdvice(() -> ((AfterReturningAdvice) advice).afterReturning(result, method, objects, o)));
    }

    private void invokeThrows(final List<Advice> matchedAdvices, final Exception e) {
        matchedAdvices.stream()
                .filter(advice -> advice instanceof ThrowsAdvice).toList()
                .forEach(advice -> invokeAdvice(() -> ((ThrowsAdvice) advice).afterThrowing(e)));
    }

    private void invokeAdvice(final AdviceRunnable runnable) {
        try {
            runnable.accept();
        } catch (Throwable e) {
            throw new AdviceException(e);
        }
    }
}
