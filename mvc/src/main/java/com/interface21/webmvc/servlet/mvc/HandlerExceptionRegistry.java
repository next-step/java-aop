package com.interface21.webmvc.servlet.mvc;

import com.interface21.webmvc.servlet.ModelAndView;
import com.interface21.webmvc.servlet.mvc.tobe.HandlerExceptionResolver;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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
        return handlerExceptionResolvers.stream()
                                        .map(resolver -> {
                                            try {
                                                return resolver.resolveException(request, response, throwable);
                                            } catch (Exception e) {
                                                log.error("Exception while resolving exception: {}", e.getMessage(), e);
                                                return null;
                                            }
                                        })
                                        .filter(Objects::nonNull)
                                        .findFirst();
    }

}
