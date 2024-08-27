package com.interface21.web.exception;

import com.interface21.beans.factory.BeanFactory;
import com.interface21.beans.factory.annotation.Autowired;
import com.interface21.webmvc.servlet.ModelAndView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

public class ExceptionHandlerMethodResolver {

    private static final Logger log = LoggerFactory.getLogger(ExceptionHandlerMethodResolver.class);

    private final BeanFactory beanFactory;
    private final Object advice;
    private final Method method;

    @Autowired
    public ExceptionHandlerMethodResolver(final BeanFactory beanFactory, final Object advice, final Method method) {
        this.beanFactory = beanFactory;
        this.advice = advice;
        this.method = method;
    }

    public ModelAndView resolveException(final Throwable t) {
        final Object[] params = populateArguments(method.getParameterTypes(), t);

        try {
            return (ModelAndView) method.invoke(advice, params);
        } catch (IllegalAccessException | InvocationTargetException e) {
            log.error("{} ExceptionHandlerMethod not invoked.", t, e);
            return null;
        }
    }

    private Object[] populateArguments(final Class<?>[] parameterTypes, final Throwable ex) {
        return Arrays.stream(parameterTypes)
                .map(type -> {
                    if (type.isInstance(ex)) {
                        return ex;
                    }
                    return beanFactory.getBean(type);
                })
                .toArray();
    }
}
