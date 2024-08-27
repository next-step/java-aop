package com.interface21.web.code;

import com.interface21.beans.factory.BeanFactory;
import com.interface21.beans.factory.config.BeanPostProcessor;

import java.util.Set;

public class FakeBeanFactory implements BeanFactory {
    @Override
    public Set<Class<?>> getBeanClasses() {
        return Set.of();
    }

    @Override
    public <T> T getBean(final Class<T> clazz) {
        return null;
    }

    @Override
    public void clear() {

    }

    @Override
    public void addBeanPostProcessor(final BeanPostProcessor beanPostProcessor) {

    }
}
