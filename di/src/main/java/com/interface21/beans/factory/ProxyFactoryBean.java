package com.interface21.beans.factory;

import com.interface21.aop.advice.AroundAdvice;
import com.interface21.aop.advice.MethodInvocation;
import com.interface21.aop.advisor.Advisor;
import com.interface21.aop.advisor.Pointcut;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;

import java.util.Arrays;
import java.util.List;

public class ProxyFactoryBean<T> implements FactoryBean<T> {

    private final Class<?> targetClass;
    private final Class<?> objectType;
    private final List<Advisor> advisors;

    public ProxyFactoryBean(Class<?> targetClass, Class<T> objectType, Advisor... advisors) {
        this.targetClass = targetClass;
        this.objectType = objectType;
        this.advisors = Arrays.asList(advisors);
    }

    @Override
    public Class<?> getObjectType() {
        return objectType;
    }

    @SuppressWarnings("unchecked")
    @Override
    public T getObject() {
        return (T) createProxyBuilder().create();
    }

    private Enhancer createProxyBuilder() {
        final var enhancer = new Enhancer();
        enhancer.setSuperclass(targetClass);
        enhancer.setCallback(buildMethodInterceptor());
        return enhancer;
    }

    @SuppressWarnings("unchecked")
    @Override
    public T getObject(final Class<?>[] argumentTypes, final Object[] arguments) {
        return (T) createProxyBuilder().create(argumentTypes, arguments);
    }

    private MethodInterceptor buildMethodInterceptor() {
        return (obj, method, args, methodProxy) -> {
            MethodInvocation methodInvocation = new MethodInvocation(obj, args, methodProxy);

            if (advisors.isEmpty()) {
                return methodInvocation.proceed();
            }

            Advisor advisor = advisors.get(0);
            Pointcut pointcut = advisor.getPointcut();
            if (pointcut == null || pointcut.matches(method, targetClass)) {
                AroundAdvice advice = (AroundAdvice) advisor.getAdvice();
                return advice.invoke(methodInvocation);
            } else {
                return methodInvocation.proceed();
            }
        };
    }
}
