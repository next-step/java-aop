package com.interface21.beans.factory;

import com.interface21.beans.factory.config.BeanPostProcessor;

public interface ConfigurableListableBeanFactory extends BeanFactory {
    void addPostProcessor(BeanPostProcessor beanPostProcessor);

    void preInstantiateSingletons();
}
