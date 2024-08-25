package com.interface21.webmvc.servlet.mvc.tobe;

import com.interface21.context.ApplicationContext;
import com.interface21.context.stereotype.Controller;
import com.interface21.context.stereotype.ControllerAdvice;
import com.interface21.web.bind.annotation.RequestMethod;
import com.interface21.webmvc.servlet.mvc.HandlerMapping;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class AnnotationHandlerMapping implements HandlerMapping {

    private static final Logger log = LoggerFactory.getLogger(AnnotationHandlerMapping.class);

    private final ApplicationContext applicationContext;
    private final HandlerConverter handlerConverter;
    private final ControllerAdviceConverter controllerAdviceConverter;
    private final Map<HandlerKey, HandlerExecution> handlerExecutions = new HashMap<>();

    public AnnotationHandlerMapping(final ApplicationContext applicationContext, final HandlerConverter handlerConverter,
                                    final ControllerAdviceConverter controllerAdviceConverter) {
        this.applicationContext = applicationContext;
        this.handlerConverter = handlerConverter;
        this.controllerAdviceConverter = controllerAdviceConverter;
    }

    public void initialize() {
        Map<Class<?>, Object> controllers = getControllers(applicationContext);
        handlerExecutions.putAll(handlerConverter.convert(controllers));
        log.info("Initialized AnnotationHandlerMapping!");
    }

    private Map<Class<?>, Object> getControllers(ApplicationContext ac) {
        final var controllers = new HashMap<Class<?>, Object>();
        for (Class<?> clazz : ac.getBeanClasses()) {
            final var annotation = clazz.getAnnotation(Controller.class);
            if (annotation != null) {
                controllers.put(clazz, ac.getBean(clazz));
            }
        }
        return controllers;
    }

    private Map<Class<?>, Object> getControllerAdvice(ApplicationContext ac) {
        final var advices = new HashMap<Class<?>, Object>();
        for (Class<?> clazz : ac.getBeanClasses()) {
            final var annotation = clazz.getAnnotation(ControllerAdvice.class);
            if (annotation != null) {
                advices.put(clazz, ac.getBean(clazz));
            }
        }
        return advices;
    }

    public HandlerExecution getHandler(final HttpServletRequest request) {
        final var requestUri = request.getRequestURI();
        final var requestMethod = RequestMethod.valueOf(request.getMethod().toUpperCase());
        log.debug("requestUri : {}, requestMethod : {}", requestUri, requestMethod);
        return getHandlerInternal(new HandlerKey(requestUri, requestMethod));
    }

    public Map<Class<? extends Throwable>, HandlerExecution> getExceptionHandlers() {
        return controllerAdviceConverter.convert(getControllerAdvice(applicationContext));
    }

    private HandlerExecution getHandlerInternal(final HandlerKey requestHandlerKey) {
        for (HandlerKey handlerKey : handlerExecutions.keySet()) {
            if (handlerKey.isMatch(requestHandlerKey)) {
                return handlerExecutions.get(handlerKey);
            }
        }
        return null;
    }
}
