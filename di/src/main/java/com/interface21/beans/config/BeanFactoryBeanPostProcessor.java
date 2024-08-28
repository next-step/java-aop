package com.interface21.beans.config;

import com.interface21.beans.factory.BeanFactory;

public interface BeanFactoryBeanPostProcessor extends BeanPostProcessor {
    void injectBeanFactory(final BeanFactory beanFactory);
}
