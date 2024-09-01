package com.interface21.webmvc.servlet.mvc;

import com.interface21.webmvc.servlet.ModelAndView;
import com.interface21.webmvc.servlet.mvc.tobe.HandlerExceptionResolver;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HandlerExceptionRegistry {

    private static final Logger log = LoggerFactory.getLogger(HandlerExceptionRegistry.class);
    private final List<HandlerExceptionResolver> handlerExceptionResolvers = new ArrayList<>();

    public void addHandlerExceptionResolver(HandlerExceptionResolver handlerExceptionResolver) {
        handlerExceptionResolvers.add(handlerExceptionResolver);
    }

    public Optional<ModelAndView> handle(HttpServletRequest request, HttpServletResponse response, Throwable throwable) {
        for (HandlerExceptionResolver handlerExceptionResolver : handlerExceptionResolvers) {
            Optional<ModelAndView> modelAndView = findModelAndView(request, response, throwable, handlerExceptionResolver);
            if (modelAndView.isPresent()) {
                return modelAndView;
            }
        }
        return Optional.empty();
    }

    private static Optional<ModelAndView> findModelAndView(HttpServletRequest request, HttpServletResponse response, Throwable throwable,
                                                           HandlerExceptionResolver handlerExceptionResolver) {
        if (handlerExceptionResolver.supports(throwable)) {
            try {
                return Optional.of(handlerExceptionResolver.resolveException(request, response, throwable));
            } catch (Exception e) {
                log.error("Exception occurred while resolving exception", e);
            }
        }
        return Optional.empty();
    }
}
