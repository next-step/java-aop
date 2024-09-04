package com.interface21.webmvc.servlet.mvc;

import com.interface21.webmvc.servlet.ModelAndView;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.ArrayList;
import java.util.List;

public class HandlerExceptionRegistry {
    private final List<HandlerExceptionResolver> handlerExceptionResolvers;

    public HandlerExceptionRegistry() {
        this.handlerExceptionResolvers = new ArrayList<>();
    }

    public void addResolver(final HandlerExceptionResolver handlerExceptionResolver) {
        handlerExceptionResolvers.add(handlerExceptionResolver);
    }

    public ModelAndView resolve(final HttpServletRequest request, final HttpServletResponse response, final Throwable throwable) throws Throwable {
        return handlerExceptionResolvers.stream()
                .filter(resolver -> resolver.supports(throwable))
                .findFirst()
                .orElseThrow(() -> throwable)
                .resolveException(request, response, throwable);
    }
}
