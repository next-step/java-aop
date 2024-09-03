package com.interface21.webmvc.servlet.mvc.tobe;

import com.interface21.web.method.support.HandlerMethodArgumentResolver;
import com.interface21.webmvc.servlet.mvc.ExceptionHandler;
import com.interface21.webmvc.servlet.mvc.ExceptionHandlerExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class HandlerExceptionConverter {

    private static final Logger log = LoggerFactory.getLogger(HandlerExceptionConverter.class);

    private final List<HandlerMethodArgumentResolver> argumentResolvers = new ArrayList<>();

    public void setArgumentResolvers(final List<HandlerMethodArgumentResolver> argumentResolvers) {
        this.argumentResolvers.addAll(argumentResolvers);
    }

    public void addArgumentResolver(final HandlerMethodArgumentResolver argumentResolver) {
        this.argumentResolvers.add(argumentResolver);
    }

    public Map<Class<? extends Throwable>, ExceptionHandlerExecution> convert(final Map<Class<?>, Object> controllerAdvices) {
        final Map<Class<? extends Throwable>, ExceptionHandlerExecution> handlers = new HashMap<>();
        final Set<Class<?>> controllerAdviceClasses = controllerAdvices.keySet();
        for (final Class<?> controllerAdviceClass : controllerAdviceClasses) {
            final Object target = controllerAdvices.get(controllerAdviceClass);
            addHandlerExceptionExecution(handlers, target, controllerAdviceClass.getMethods());
        }

        return handlers;
    }

    private void addHandlerExceptionExecution(final Map<Class<? extends Throwable>, ExceptionHandlerExecution> handlerExecutions,
                                              final Object target,
                                              final Method[] methods) {
        Arrays.stream(methods)
                .filter(method -> method.isAnnotationPresent(ExceptionHandler.class))
                .forEach(method -> {
                    final var exceptions = method.getAnnotation(ExceptionHandler.class);
                    handlerExecutions.putAll(createHandlerExceptionExecutions(target, method, exceptions));
                });
    }

    private Map<Class<? extends Throwable>, ExceptionHandlerExecution> createHandlerExceptionExecutions(final Object target, final Method method, final ExceptionHandler exceptionHandler) {
        return Arrays.stream(exceptionHandler.value())
                .collect(Collectors.toMap(
                        handlerKey -> handlerKey,
                        handlerKey -> new ExceptionHandlerExecution(argumentResolvers, target, method)
                ));
    }
}
