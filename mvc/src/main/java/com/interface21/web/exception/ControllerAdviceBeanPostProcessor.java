package com.interface21.web.exception;

import com.interface21.beans.factory.BeanFactory;
import com.interface21.beans.config.BeanFactoryBeanPostProcessor;
import com.interface21.web.bind.annotation.ControllerAdvice;

public class ControllerAdviceBeanPostProcessor implements BeanFactoryBeanPostProcessor {

    private BeanFactory beanFactory;

    @Override
    public Object postInitialization(final Object bean) {
        final Class<?> clazz = bean.getClass();

        if (!isControllerAdvice(clazz)) {
            return bean;
        }

        final ExceptionHandlerExceptionResolver handlerExceptionResolver = beanFactory.getBean(ExceptionHandlerExceptionResolver.class);
        handlerExceptionResolver.appendAdvice(clazz, bean);

        return bean;
    }

    @Override
    public void injectBeanFactory(final BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    private boolean isControllerAdvice(final Class<?> clazz) {
        return clazz.isAnnotationPresent(ControllerAdvice.class);
    }
}
