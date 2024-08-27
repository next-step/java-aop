package com.interface21.beans.factory.config;

public abstract class BeanFactoryBeanPostProcessor implements BeanPostProcessor {
    public abstract void injectBeanFactory(final Object beanFactory);
}
