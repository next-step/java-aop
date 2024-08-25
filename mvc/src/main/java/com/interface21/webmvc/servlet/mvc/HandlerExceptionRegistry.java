package com.interface21.webmvc.servlet.mvc;

import com.interface21.webmvc.servlet.ModelAndView;
import com.interface21.webmvc.servlet.mvc.tobe.HandlerExceptionResolver;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class HandlerExceptionRegistry {

    private final List<HandlerExceptionResolver> handlerExceptionResolvers = new ArrayList<>();

    public void addHandlerExceptionResolver(HandlerExceptionResolver handlerExceptionResolver) {
        handlerExceptionResolvers.add(handlerExceptionResolver);
    }

    public Optional<ModelAndView> handle(HttpServletRequest request, HttpServletResponse response, Throwable ex) {
        return handlerExceptionResolvers.stream()
            .map(he -> {
                try {
                    return he.resolveException(request, response, ex);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            })
            .findFirst();
    }
}
