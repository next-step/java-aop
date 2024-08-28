package com.interface21.beans.factory;

import com.interface21.beans.factory.proxy.Advisor;
import com.interface21.beans.factory.proxy.TypeTarget;
import com.interface21.beans.factory.support.BeanFactoryUtils;
import com.interface21.framework.CglibAopProxy;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProxyFactoryBean implements FactoryBean<Object> {

    private TypeTarget<?> typeTarget;
    private final List<Advisor> advisors = new ArrayList<>();
    private final BeanFactory beanFactory;

    public ProxyFactoryBean(final BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    public void setTargetClass(final TypeTarget<?> typeTarget) {
        this.typeTarget = typeTarget;
    }

    public void addAdvisors(final Advisor... advisors) {
        this.advisors.addAll(List.of(advisors));
    }

    @Override
    public Object getObject() throws Exception {
        final Class<?> targetClass = typeTarget.getTargetClass();
        final Constructor<?> constructor = BeanFactoryUtils.getInjectedConstructor(targetClass);

        if (constructor == null) {
            return new CglibAopProxy(targetClass, advisors).getProxy();
        }

        final Class<?>[] parameterTypes = constructor.getParameterTypes();

        return new CglibAopProxy(targetClass, advisors, parameterTypes, populateArguments(parameterTypes)).getProxy();
    }

    @Override
    public Class<?> getObjectType() {
        return typeTarget.getTargetClass();
    }

    private Object[] populateArguments(final Class<?>[] parameterTypes) {
        return Arrays.stream(parameterTypes)
                .map(beanFactory::getBean)
                .toArray();
    }
}
