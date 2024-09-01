package com.interface21.beans.factory;

import com.interface21.aop.advice.AroundAdvice;
import com.interface21.aop.advice.MethodInvocation;
import com.interface21.aop.advisor.Advisor;
import com.interface21.aop.advisor.Pointcut;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;

import java.util.ArrayList;
import java.util.List;

public class ProxyFactoryBean<T> implements FactoryBean<T> {

    private Class<?> targetClass;
    private Class<?> objectType;
    private final List<Advisor> advisors = new ArrayList<>();

    public void setTargetClass(Class<?> targetClass) {
        this.targetClass = targetClass;
    }

    public void addAdvisor(Advisor advisor) {
        this.advisors.add(advisor);
    }

    @Override
    public Class<?> getObjectType() {
        return objectType;
    }

    public void setObjectType(Class<?> objectType) {
        this.objectType = objectType;
    }

    @SuppressWarnings("unchecked")
    @Override
    public T getObject() {
        final var enhancer = new Enhancer();
        enhancer.setSuperclass(targetClass);
        enhancer.setCallback(buildMethodInterceptor());
        return (T) enhancer.create();
    }

    @SuppressWarnings("unchecked")
    @Override
    public T getObject(final Class<?>[] argumentTypes, final Object[] arguments) {
        final var enhancer = new Enhancer();
        enhancer.setSuperclass(targetClass);
        enhancer.setCallback(buildMethodInterceptor());
        return (T) enhancer.create(argumentTypes, arguments);
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
