package com.interface21.beans.factory;

import com.interface21.beans.factory.config.BeanPostProcessor;

import java.util.Set;

public interface BeanFactory {
    Set<Class<?>> getBeanClasses();

    <T> T getBean(Class<T> clazz);

    void clear();

    void addBeanPostProcessor(final BeanPostProcessor beanPostProcessor);
}
