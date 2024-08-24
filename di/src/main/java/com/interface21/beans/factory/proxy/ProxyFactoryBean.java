package com.interface21.beans.factory.proxy;

import com.interface21.beans.factory.BeanFactory;
import com.interface21.beans.factory.FactoryBean;
import net.sf.cglib.proxy.Enhancer;

import java.util.Arrays;

public class ProxyFactoryBean<T> implements FactoryBean<T> {

    private final Target<T> target;
    private final Advisor advisor;
    private final BeanFactory beanFactory;

    public ProxyFactoryBean(Target<T> target, Advisor advisor, BeanFactory beanFactory) {
        this.target = target;
        this.advisor = advisor;
        this.beanFactory = beanFactory;
    }

    @Override
    public T getObject() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(target.getType());
        enhancer.setCallback(advisor.createMethodInterceptor());

        return (T) enhancer.create(target.getConstructorParameterTypes(), parseTargetConstructorParameters());
    }

    private Object[] parseTargetConstructorParameters() {
        return Arrays.stream(target.getConstructorParameterTypes())
                .map(beanFactory::getBean)
                .toArray();
    }

    @Override
    public Class<T> getType() {
        return target.getType();
    }
}
