package com.interface21.webmvc.servlet.mvc.tobe;

import com.interface21.web.bind.annotation.ExceptionHandler;
import com.interface21.web.method.support.HandlerMethodArgumentResolver;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ControllerAdviceConverter {

    private static final Logger log = LoggerFactory.getLogger(ControllerAdviceConverter.class);

    private List<HandlerMethodArgumentResolver> argumentResolvers = new ArrayList<>();

    public void setArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        this.argumentResolvers.addAll(argumentResolvers);
    }

    public Map<Class<? extends Throwable>, HandlerExecution> convert(Map<Class<?>, Object> controllerAdvices) {
        Map<Class<? extends Throwable>, HandlerExecution> exceptionHandlers = new HashMap<>();

        for (Map.Entry<Class<?>, Object> entry : controllerAdvices.entrySet()) {
            Class<?> adviceClass = entry.getKey();
            Object adviceInstance = entry.getValue();
            addExceptionHandlerExecution(exceptionHandlers, adviceInstance, adviceClass.getMethods());
        }

        return exceptionHandlers;
    }

    private void addExceptionHandlerExecution(Map<Class<? extends Throwable>, HandlerExecution> exceptionHandlers,
                                              final Object target,
                                              Method[] methods) {
        Arrays.stream(methods)
            .filter(method -> method.isAnnotationPresent(ExceptionHandler.class))
            .forEach(method -> registerExceptionHandler(exceptionHandlers, target, method));
    }

    private void registerExceptionHandler(Map<Class<? extends Throwable>, HandlerExecution> exceptionHandlers,
                                          final Object target,
                                          Method method) {
        ExceptionHandler exceptionHandler = method.getAnnotation(ExceptionHandler.class);
        for (Class<? extends Throwable> exceptionType : exceptionHandler.value()) {
            exceptionHandlers.put(exceptionType, new HandlerExecution(new ArrayList<>(), target, method));
        }
    }
}

