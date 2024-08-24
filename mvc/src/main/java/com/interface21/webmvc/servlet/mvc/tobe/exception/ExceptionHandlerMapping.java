package com.interface21.webmvc.servlet.mvc.tobe.exception;

import com.interface21.context.ApplicationContext;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class ExceptionHandlerMapping {

    private final ApplicationContext applicationContext;
    private final ExceptionHandlerConverter exceptionHandlerConverter;
    private final Map<ExceptionHandlerKey, ExceptionHandlerExecution> handlerExecutions = new HashMap<>();

    public ExceptionHandlerMapping(ApplicationContext applicationContext, ExceptionHandlerConverter exceptionHandlerConverter) {
        this.applicationContext = applicationContext;
        this.exceptionHandlerConverter = exceptionHandlerConverter;
    }

    public void initialize() {
        Map<Class<?>, Object> controllerAdvices = getControllerAdvices();
        handlerExecutions.putAll(exceptionHandlerConverter.convert(controllerAdvices));
    }

    private Map<Class<?>, Object> getControllerAdvices() {
        return applicationContext.getBeanClasses()
                .stream()
                .filter(clazz -> clazz.isAnnotationPresent(ControllerAdvice.class))
                .collect(Collectors.toMap(clazz -> clazz, applicationContext::getBean));
    }

    public Map<ExceptionHandlerKey, ExceptionHandlerExecution> getHandlerExecutions() {
        return handlerExecutions;
    }
}
