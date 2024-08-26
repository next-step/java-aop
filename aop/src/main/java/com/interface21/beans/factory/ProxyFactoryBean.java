package com.interface21.beans.factory;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class ProxyFactoryBean<T> implements FactoryBean<T> {

    private final Class<T> clazz;
    private final List<Advisor> advisors;

    public ProxyFactoryBean(final Class<T> clazz) {
        this.clazz = clazz;
        this.advisors = new ArrayList<>();
    }

    public void addAdvisors(final Advisor advisor) {
        this.advisors.add(advisor);
    }

    @Override
    @SuppressWarnings("unchecked")
    public T getObject() {
        final Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(clazz);
        enhancer.setCallback(createBeanMethodInterceptor());
        return (T) enhancer.create();
    }

    private MethodInterceptor createBeanMethodInterceptor() {
        return (o, method, objects, methodProxy) -> {
            final List<Advice> advice = getTargetAdvice(method, objects);

            executeBeforeAdvices(advice, method, objects, methodProxy);

            final Object result = methodProxy.invokeSuper(o, objects);

            executeAfterAdvices(advice, method, objects, methodProxy, result);

            return result;
        };
    }

    private void executeBeforeAdvices(final List<Advice> advice, final Method method, final Object[] objects, final MethodProxy methodProxy) throws Throwable {
        for (final Advice targetAdvice : advice) {
            if (targetAdvice instanceof final MethodBeforeAdvice methodBeforeAdvice) {
                methodBeforeAdvice.before(method, objects, methodProxy);
            }
        }
    }

    private void executeAfterAdvices(final List<Advice> advice, final Method method, final Object[] objects, final MethodProxy methodProxy, final Object result) throws Throwable {
        for (final Advice targetAdvice : advice) {
            if (targetAdvice instanceof final AfterReturningAdvice afterReturningAdvice) {
                afterReturningAdvice.afterReturning(result, method, objects, methodProxy);
            }
        }
    }

    private List<Advice> getTargetAdvice(final Method method, final Object[] objects) {
        return advisors.stream()
                .filter(advisor -> advisor.getPointCut().getMethodMatcher().matches(method, clazz, objects))
                .map(Advisor::getAdvice)
                .toList();
    }
}
