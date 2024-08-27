package com.interface21.context;

import com.interface21.beans.factory.config.BeanPostProcessor;
import java.util.Set;

public interface ApplicationContext {
    <T> T getBean(Class<T> clazz);

    Set<Class<?>> getBeanClasses();

    void addBeanPostProcessor(BeanPostProcessor beanPostProcessor);
}
