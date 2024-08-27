package com.interface21.webmvc.servlet.mvc;

import com.interface21.context.ApplicationContext;
import com.interface21.web.exception.ExceptionHandlerExceptionResolver;
import com.interface21.web.exception.HandlerExceptionResolver;
import com.interface21.webmvc.servlet.ModelAndView;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.ArrayList;
import java.util.List;

public class HandlerExceptionRegistry {

    private final List<HandlerExceptionResolver> resolvers = new ArrayList<>();

    public void initApplicationContext(final ApplicationContext applicationContext) {
        final ExceptionHandlerExceptionResolver exceptionResolver = applicationContext.getBean(ExceptionHandlerExceptionResolver.class);
        resolvers.add(exceptionResolver);
    }

    public ModelAndView resolveException(final HttpServletRequest req, final HttpServletResponse res, final Object handler, final Throwable ex) {
        for (final HandlerExceptionResolver resolver : resolvers) {
            final ModelAndView mv = resolver.resolveException(req, res, handler, ex);

            if (mv != null) {
                return mv;
            }
        }

        return null;
    }
}
