package com.interface21.web.code;

import com.interface21.beans.factory.BeanFactory;
import com.interface21.beans.factory.config.BeanPostProcessor;
import com.interface21.context.ApplicationContext;
import com.interface21.web.exception.ExceptionHandlerExceptionResolver;

import java.util.Set;

public class FakeApplicationContext implements ApplicationContext, BeanFactory {
    @Override
    public <T> T getBean(final Class<T> clazz) {
        if (clazz.equals(ExceptionHandlerExceptionResolver.class)) {
            final ExceptionHandlerExceptionResolver resolver = new ExceptionHandlerExceptionResolver(this);

            resolver.appendAdvice(ExControllerAdvice.class, new ExControllerAdvice());

            return (T) resolver;
        }
        return null;
    }

    @Override
    public void clear() {

    }

    @Override
    public void addBeanPostProcessor(final BeanPostProcessor beanPostProcessor) {

    }

    @Override
    public Set<Class<?>> getBeanClasses() {
        return Set.of();
    }
}
