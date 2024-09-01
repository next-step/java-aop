package com.interface21.webmvc.servlet.mvc.tobe;

import com.interface21.web.bind.annotation.ExceptionHandler;
import com.interface21.web.method.support.HandlerMethodArgumentResolver;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExceptionHandlerConverter {

    private List<HandlerMethodArgumentResolver> argumentResolvers = new ArrayList<>();

    public ExceptionHandlerConverter() {
    }

    public ExceptionHandlerConverter(List<HandlerMethodArgumentResolver> argumentResolvers) {
        this.argumentResolvers = argumentResolvers;
    }

    public Map<Class<? extends Throwable>, HandlerExecution> convert(Map<Class<?>, Object> controllerAdvices) {
        Map<Class<? extends Throwable>, HandlerExecution> exceptionHandlers = new HashMap<>();
        for (Map.Entry<Class<?>, Object> entry : controllerAdvices.entrySet()) {
            Class<?> clazz = entry.getKey();
            Object target = entry.getValue();
            addExceptionHandler(exceptionHandlers, target, clazz.getMethods());
        }
        return exceptionHandlers;
    }

    private void addExceptionHandler(Map<Class<? extends Throwable>, HandlerExecution> exceptionHandlers, Object target, Method[] methods) {
        Arrays.stream(methods)
              .filter(method -> method.isAnnotationPresent(ExceptionHandler.class))
              .forEach(method -> {
                  registerExceptionHandler(exceptionHandlers, target, method);
              });
    }

    private void registerExceptionHandler(Map<Class<? extends Throwable>, HandlerExecution> exceptionHandlers, Object target, Method method) {
        final var exceptionHandler = method.getAnnotation(ExceptionHandler.class);
        for (Class<? extends Throwable> exception : exceptionHandler.value()) {
            exceptionHandlers.put(exception, new HandlerExecution(new ArrayList<>(), target, method));
        }
    }
}
