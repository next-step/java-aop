package com.interface21.webmvc.servlet.mvc.tobe;

import com.interface21.webmvc.servlet.ModelAndView;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Map;

public class DefaultHandlerExceptionResolver implements HandlerExceptionResolver {

    private final Map<Class<? extends Throwable>, HandlerExecution> handlerExecutions;

    public DefaultHandlerExceptionResolver(Map<Class<? extends Throwable>, HandlerExecution> handlerExecutions) {
        this.handlerExecutions = handlerExecutions;
    }

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Throwable throwable) throws Exception {
        Class<?> exceptionClazz = throwable.getClass();

        while (exceptionClazz != null) {
            HandlerExecution handlerExecution = handlerExecutions.get(exceptionClazz);
            if (handlerExecution != null) {
                return handlerExecution.handle(request, response, throwable);
            }
            exceptionClazz = exceptionClazz.getSuperclass();
        }

        return null;
    }

    @Override
    public boolean supports(Throwable throwable) {
        return handlerExecutions.containsKey(throwable.getClass());
    }
}
