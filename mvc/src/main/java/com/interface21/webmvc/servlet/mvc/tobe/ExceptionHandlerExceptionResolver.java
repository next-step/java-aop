package com.interface21.webmvc.servlet.mvc.tobe;

import com.interface21.webmvc.servlet.ModelAndView;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

public class ExceptionHandlerExceptionResolver implements HandlerExceptionResolver {

    private final Map<Class<? extends Throwable>, HandlerExecution> handlerExecutions;

    public ExceptionHandlerExceptionResolver(final Map<Class<? extends Throwable>, HandlerExecution> handlerExecutions) {
        this.handlerExecutions = new HashMap<>(handlerExecutions);
    }


    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Throwable t) throws Exception {
        Class<?> exceptionClass = t.getClass();

        while (exceptionClass != null) {
            final var handlerExecution = handlerExecutions.get(exceptionClass);
            if (handlerExecution != null) {
                return handlerExecution.handle(request, response, t);
            }
            exceptionClass = exceptionClass.getSuperclass();
        }
        return null;
    }
}
