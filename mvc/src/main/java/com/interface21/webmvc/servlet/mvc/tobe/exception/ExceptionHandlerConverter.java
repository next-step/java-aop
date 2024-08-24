package com.interface21.webmvc.servlet.mvc.tobe.exception;

import com.interface21.web.method.support.HandlerMethodArgumentResolver;
import com.interface21.webmvc.servlet.mvc.tobe.HandlerExecution;
import com.interface21.webmvc.servlet.mvc.tobe.support.ExceptionArgumentResolver;
import com.interface21.webmvc.servlet.mvc.tobe.support.HttpRequestArgumentResolver;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class ExceptionHandlerConverter {

    private List<HandlerMethodArgumentResolver> argumentResolvers = List.of(
            new HttpRequestArgumentResolver(),
            new HttpRequestArgumentResolver(),
            new ExceptionArgumentResolver()
    );

    public Map<ExceptionHandlerKey, HandlerExecution> convert(Map<Class<?>, Object> controllerAdvices) {
        Map<ExceptionHandlerKey, HandlerExecution> handlers = new HashMap<>();
        for (Entry<Class<?>, Object> adviceEntry : controllerAdvices.entrySet()) {
            addExceptionHandlerExecution(handlers, adviceEntry);
        }
        return handlers;
    }

    private void addExceptionHandlerExecution(Map<ExceptionHandlerKey, HandlerExecution> handlers,
                                              Entry<Class<?>, Object> adviceEntry) {
        Class<?> controllerAdvice = adviceEntry.getKey();
        List<Method> methods = Arrays.stream(controllerAdvice.getMethods())
                .filter(method -> method.isAnnotationPresent(ExceptionHandler.class))
                .toList();
        for (Method method : methods) {
            handlers.putAll(createExceptionHandlerExecutions(adviceEntry.getValue(), method));
        }
    }

    private Map<ExceptionHandlerKey, HandlerExecution> createExceptionHandlerExecutions(Object targetBean, Method method) {
        ExceptionHandler exceptionHandler = method.getAnnotation(ExceptionHandler.class);
        return Arrays.stream(exceptionHandler.value())
                .map(ExceptionHandlerKey::new)
                .collect(Collectors.toMap(
                        handlerKey -> handlerKey,
                        handlerKey -> new HandlerExecution(argumentResolvers, targetBean, method)
                ));
    }
}
