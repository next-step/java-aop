package com.interface21.beans.factory.proxy;

import com.interface21.beans.factory.FactoryBean;
import com.interface21.framework.CglibAopProxy;
import net.sf.cglib.proxy.MethodInterceptor;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class ProxyFactoryBean implements FactoryBean<Object> {

    private TypeTarget<?> typeTarget;
    private final List<Advisor> advisors = new ArrayList<>();

    public void setTargetClass(final TypeTarget<?> typeTarget) {
        this.typeTarget = typeTarget;
    }

    public void addAdvisors(final Advisor... advisors) {
        this.advisors.addAll(List.of(advisors));
    }

    @Override
    public Object getObject() throws Exception {
        final MethodInterceptor methodInterceptor = createMethodInterceptor();

        return new CglibAopProxy(typeTarget.getTargetClass(), methodInterceptor).getProxy();
    }

    @Override
    public Class<?> getObjectType() {
        return typeTarget.getTargetClass();
    }

    private MethodInterceptor createMethodInterceptor() {
        final Class<?> clazz = typeTarget.getTargetClass();

        return (o, method, objects, methodProxy) -> {
            final List<Advice> matchedAdvices = this.advisors.stream().filter(advisor -> advisor.getPointcut().matches(method, clazz)).map(Advisor::getAdvice).toList();

            invokeMethodBefore(o, method, objects, matchedAdvices);

            final Object result = methodProxy.invokeSuper(o, objects);

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

    private void invokeAdvice(final AdviceRunnable runnable) {
        try {
            runnable.accept();
        } catch (Throwable e) {
            throw new AdviceException(e);
        }
    }
}
