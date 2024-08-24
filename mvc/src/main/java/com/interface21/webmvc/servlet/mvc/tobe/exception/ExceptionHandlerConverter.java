package com.interface21.webmvc.servlet.mvc.tobe.exception;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class ExceptionHandlerConverter {

    public Map<ExceptionHandlerKey, ExceptionHandlerExecution> convert(Map<Class<?>, Object> controllerAdvices) {
        Map<ExceptionHandlerKey, ExceptionHandlerExecution> handlers = new HashMap<>();
        for (Entry<Class<?>, Object> adviceEntry : controllerAdvices.entrySet()) {
            addExceptionHandlerExecution(handlers, adviceEntry);
        }
        return handlers;
    }

    private void addExceptionHandlerExecution(Map<ExceptionHandlerKey, ExceptionHandlerExecution> handlers,
                                              Entry<Class<?>, Object> adviceEntry) {
        Class<?> controllerAdvice = adviceEntry.getKey();
        List<Method> methods = Arrays.stream(controllerAdvice.getMethods())
                .filter(method -> method.isAnnotationPresent(ExceptionHandler.class))
                .toList();
        for (Method method : methods) {
            handlers.putAll(createExceptionHandlerExecutions(adviceEntry.getValue(), method));
        }
    }

    private Map<ExceptionHandlerKey, ExceptionHandlerExecution> createExceptionHandlerExecutions(Object targetBean, Method method) {
        ExceptionHandler exceptionHandler = method.getAnnotation(ExceptionHandler.class);
        return Arrays.stream(exceptionHandler.value())
                .map(ExceptionHandlerKey::new)
                .collect(Collectors.toMap(
                        handlerKey -> handlerKey,
                        handlerKey -> new ExceptionHandlerExecution(targetBean, method)
                ));
    }
}
