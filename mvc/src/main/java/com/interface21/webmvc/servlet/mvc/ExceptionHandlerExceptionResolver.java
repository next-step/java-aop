package com.interface21.webmvc.servlet.mvc;

import com.interface21.webmvc.servlet.ModelAndView;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.HashMap;
import java.util.Map;

public class ExceptionHandlerExceptionResolver implements HandlerExceptionResolver {

    private final Map<Class<? extends Throwable>, ExceptionHandlerExecution> exceptionHandlers;

    public ExceptionHandlerExceptionResolver() {
        this(new HashMap<>());
    }

    public ExceptionHandlerExceptionResolver(final Map<Class<? extends Throwable>, ExceptionHandlerExecution> handlerExecutions) {
        this.exceptionHandlers = new HashMap<>(handlerExecutions);
    }

    @Override
    public ModelAndView resolveException(final HttpServletRequest request, final HttpServletResponse response, final Throwable throwable) {
        Class<?> exceptionClass = throwable.getClass();

        while (exceptionClass != null) {
            final ExceptionHandlerExecution handlerExecution = exceptionHandlers.get(exceptionClass);
            if (handlerExecution != null) {
                return (ModelAndView) handlerExecution.handle(throwable);
            }
            exceptionClass = exceptionClass.getSuperclass();
        }
        return null;
    }

    @Override
    public boolean supports(final Throwable throwable) {
        return exceptionHandlers.containsKey(throwable.getClass());
    }

    @Override
    public void addAll(final Map<Class<? extends Throwable>, ExceptionHandlerExecution> convert) {
        this.exceptionHandlers.putAll(convert);
    }
}
