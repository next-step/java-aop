package com.interface21.aop;

import com.interface21.aop.advice.AroundAdvice;
import com.interface21.aop.advice.MethodInvocation;
import com.interface21.aop.advisor.Advisor;
import com.interface21.aop.advisor.Pointcut;
import com.interface21.beans.factory.FactoryBean;
import net.sf.cglib.proxy.MethodInterceptor;

import java.util.ArrayList;
import java.util.List;

public class ProxyFactoryBean<T> implements FactoryBean<T> {

    private Class<?> targetClass;
    private Class<T> objectType;
    private final List<Advisor> advisors = new ArrayList<>();

    public void setTargetClass(Class<?> targetClass) {
        this.targetClass = targetClass;
    }

    public void addAdvisor(Advisor advisor) {
        this.advisors.add(advisor);
    }

    @SuppressWarnings("unchecked")
    @Override
    public T getObject() {
        ProxyFactory proxyFactory = new ProxyFactory(
                targetClass,
                buildMethodInterceptor()
        );
        return (T) proxyFactory.getProxy();
    }

    private MethodInterceptor buildMethodInterceptor() {
        return (obj, method, args, methodProxy) -> {
            MethodInvocation methodInvocation = new MethodInvocation(obj, args, methodProxy);

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

    public void setObjectType(Class<T> objectType) {
        this.objectType = objectType;
    }

    @Override
    public Class<T> getObjectType() {
        return objectType;
    }
}
