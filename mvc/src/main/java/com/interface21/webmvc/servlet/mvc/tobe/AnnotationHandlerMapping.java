package com.interface21.webmvc.servlet.mvc.tobe;

import com.interface21.context.ApplicationContext;
import com.interface21.context.stereotype.Controller;
import com.interface21.context.stereotype.ControllerAdvice;
import com.interface21.web.bind.annotation.RequestMethod;
import com.interface21.webmvc.servlet.mvc.HandlerMapping;
import jakarta.servlet.http.HttpServletRequest;
import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AnnotationHandlerMapping implements HandlerMapping {

    private static final Logger log = LoggerFactory.getLogger(AnnotationHandlerMapping.class);

    private final ApplicationContext applicationContext;
    private final HandlerConverter handlerConverter;
    private final ExceptionHandlerConverter exceptionHandlerConverter;
    private final Map<HandlerKey, HandlerExecution> handlerExecutions = new HashMap<>();

    public AnnotationHandlerMapping(final ApplicationContext applicationContext, final HandlerConverter handlerConverter,
                                    final ExceptionHandlerConverter exceptionHandlerConverter) {
        this.applicationContext = applicationContext;
        this.handlerConverter = handlerConverter;
        this.exceptionHandlerConverter = exceptionHandlerConverter;
    }

    public void initialize() {
        Map<Class<?>, Object> controllers = getControllers(applicationContext);
        handlerExecutions.putAll(handlerConverter.convert(controllers));
        log.info("Initialized AnnotationHandlerMapping!");
    }

    private Map<Class<?>, Object> getControllers(ApplicationContext ac) {
        return getAnnotatedBeans(ac, Controller.class);
    }

    private Map<Class<?>, Object> getControllerAdvices(ApplicationContext ac) {
        return getAnnotatedBeans(ac, ControllerAdvice.class);
    }

    private HashMap<Class<?>, Object> getAnnotatedBeans(ApplicationContext ac, Class<? extends Annotation> annotationClazz) {
        final var controllerAdvices = new HashMap<Class<?>, Object>();
        for (Class<?> clazz : ac.getBeanClasses()) {
            final var annotation = clazz.getAnnotation(annotationClazz);
            if (annotation != null) {
                controllerAdvices.put(clazz, ac.getBean(clazz));
            }
        }
        return controllerAdvices;
    }

    public HandlerExecution getHandler(final HttpServletRequest request) {
        final var requestUri = request.getRequestURI();
        final var requestMethod = RequestMethod.valueOf(request.getMethod().toUpperCase());
        log.debug("requestUri : {}, requestMethod : {}", requestUri, requestMethod);
        return getHandlerInternal(new HandlerKey(requestUri, requestMethod));
    }

    public Map<Class<? extends Throwable>, HandlerExecution> getExceptionHandlers() {
        return exceptionHandlerConverter.convert(getControllerAdvices(applicationContext));
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
