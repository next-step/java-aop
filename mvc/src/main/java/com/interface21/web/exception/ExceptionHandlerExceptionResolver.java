package com.interface21.web.exception;

import com.interface21.beans.factory.BeanFactory;
import com.interface21.beans.factory.annotation.Autowired;
import com.interface21.web.bind.annotation.ExceptionHandler;
import com.interface21.webmvc.servlet.ModelAndView;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ExceptionHandlerExceptionResolver implements HandlerExceptionResolver {

    private final Map<Class<? extends Throwable>, ExceptionHandlerMethodResolver> mappedMethodResolvers = new HashMap<>();
    private final BeanFactory beanFactory;

    @Autowired
    public ExceptionHandlerExceptionResolver(final BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    @Override
    public ModelAndView resolveException(final HttpServletRequest req, final HttpServletResponse res, final Object handler, final Throwable t) {
        final ExceptionHandlerMethodResolver methodResolver = mappedMethodResolvers.get(t.getClass());

        if (methodResolver == null) {
            return null;
        }

        return methodResolver.resolveException(t);
    }

    public void appendAdvice(final Class<?> clazz, final Object bean) {
        Arrays.stream(clazz.getDeclaredMethods())
                .filter(this::isExceptionHandler)
                .forEach(method -> extractMethodByException(bean, method));
    }

    private boolean isExceptionHandler(final Method method) {
        return method.isAnnotationPresent(ExceptionHandler.class);
    }

    private void extractMethodByException(final Object bean, final Method method) {
        final ExceptionHandlerMethodResolver methodResolver = new ExceptionHandlerMethodResolver(beanFactory, bean, method);
        final ExceptionHandler anno = method.getAnnotation(ExceptionHandler.class);
        Arrays.stream(anno.value()).forEach(t -> mappedMethodResolvers.put(t, methodResolver));
    }
}
