package com.interface21.beans.factory;

public interface ConfigurableListableBeanFactory extends BeanFactory {
    void preInstantiateSingletons();

    void addBeanPostProcessor(BeanPostProcessor beanPostProcessor);
}
