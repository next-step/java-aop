package com.interface21.webmvc.servlet.mvc.tobe.exception;

import com.interface21.context.ApplicationContext;
import com.interface21.context.stereotype.ControllerAdvice;
import com.interface21.webmvc.servlet.mvc.tobe.HandlerExecution;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class ExceptionHandlerMapping {

    private final ApplicationContext applicationContext;
    private final ExceptionHandlerConverter exceptionHandlerConverter;
    private final Map<ExceptionHandlerKey, HandlerExecution> handlerExecutions = new HashMap<>();

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

    public boolean accept(Throwable exception) {
        ExceptionHandlerKey handlerKey = new ExceptionHandlerKey(exception);
        return handlerExecutions.containsKey(handlerKey);
    }

    public HandlerExecution getHandler(Throwable exception) {
        ExceptionHandlerKey handlerKey = new ExceptionHandlerKey(exception);
        if (!handlerExecutions.containsKey(handlerKey)) {
            throw new IllegalArgumentException("처리가능한 exception handler가 없습니다");
        }
        return handlerExecutions.get(handlerKey);
    }

    public Map<ExceptionHandlerKey, HandlerExecution> getHandlerExecutions() {
        return handlerExecutions;
    }
}
