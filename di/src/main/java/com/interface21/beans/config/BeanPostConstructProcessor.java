package com.interface21.beans.config;

import com.interface21.beans.factory.BeanFactory;
import com.interface21.beans.factory.support.BeanFactoryUtils;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Set;

public class BeanPostConstructProcessor implements BeanPostProcessor {

    private static final Logger log = LoggerFactory.getLogger(BeanPostConstructProcessor.class);

    private final BeanFactory beanFactory;

    public BeanPostConstructProcessor(final BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    @Override
    public Object postInitialization(final Object bean) {
        initialize(bean, bean.getClass());
        return bean;
    }

    private void initialize(Object bean, Class<?> beanClass) {
        Set<Method> initializeMethods = BeanFactoryUtils.getBeanMethods(beanClass, PostConstruct.class);
        if (initializeMethods.isEmpty()) {
            return;
        }

        for (Method initializeMethod : initializeMethods) {
            log.debug("@PostConstruct Initialize Method : {}", initializeMethod);
            BeanFactoryUtils.invokeMethod(initializeMethod, bean, populateArguments(initializeMethod));
        }
    }

    private Object[] populateArguments(final Method method) {
        return Arrays.stream(method.getParameterTypes())
                .map(beanFactory::getBean)
                .toArray();
    }
}
