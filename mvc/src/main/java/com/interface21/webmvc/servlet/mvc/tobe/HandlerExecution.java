package com.interface21.webmvc.servlet.mvc.tobe;

import com.interface21.core.MethodParameter;
import com.interface21.web.method.support.HandlerMethodArgumentResolver;
import com.interface21.webmvc.servlet.ModelAndView;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HandlerExecution {

    private static final Logger log = LoggerFactory.getLogger(HandlerExecution.class);

    private static final Map<Method, MethodParameter[]> methodParameterCache = new ConcurrentHashMap<>();

    private final List<HandlerMethodArgumentResolver> argumentResolvers;
    private final Object declaredObject;
    private final Method method;

    public HandlerExecution(final List<HandlerMethodArgumentResolver> argumentResolvers,
                            final Object declaredObject,
                            final Method method) {
        this.argumentResolvers = argumentResolvers;
        this.declaredObject = declaredObject;
        this.method = method;
    }

    public ModelAndView handle(HttpServletRequest request, HttpServletResponse response) throws Exception {
        MethodParameter[] methodParameters = getMethodParameters();
        Object[] arguments = new Object[methodParameters.length];

        for (int i = 0; i < methodParameters.length; i++) {
            arguments[i] = getArguments(methodParameters[i], request, response);
        }

        return (ModelAndView) method.invoke(declaredObject, arguments);
    }

    public ModelAndView handle(HttpServletRequest request, HttpServletResponse response, Throwable t) throws Exception {
        MethodParameter[] methodParameters = getMethodParameters();
        Object[] arguments = new Object[methodParameters.length];

        for (int i = 0; i < methodParameters.length; i++) {
            arguments[i] = getArguments(methodParameters[i], request, response, t);
        }

        return (ModelAndView) method.invoke(declaredObject, arguments);
    }

    private MethodParameter[] getMethodParameters() {
        MethodParameter[] methodParameters = methodParameterCache.get(method);

        if (methodParameters == null) {
            methodParameters = new MethodParameter[method.getParameterCount()];
            Class<?>[] parameterTypes = method.getParameterTypes();
            Annotation[][] parameterAnnotations = method.getParameterAnnotations();
            String[] parameterNames = Arrays.stream(method.getParameters())
                                            .map(Parameter::getName)
                                            .toArray(String[]::new);

            for (int i = 0; i < methodParameters.length; i++) {
                methodParameters[i] = new MethodParameter(method, parameterTypes[i], parameterAnnotations[i], parameterNames[i]);
            }

            methodParameterCache.put(method, methodParameters);
        }

        return methodParameters;
    }

    private Object getArguments(MethodParameter methodParameter, HttpServletRequest request, HttpServletResponse response) {
        for (HandlerMethodArgumentResolver resolver : argumentResolvers) {
            if (resolver.supportsParameter(methodParameter)) {
                return resolver.resolveArgument(methodParameter, request, response);
            }
        }

        throw new IllegalStateException("No suitable resolver for argument: " + methodParameter.getType());
    }

    private Object getArguments(MethodParameter methodParameter, HttpServletRequest request, HttpServletResponse response, Throwable t) {
        if (methodParameter.getType().isAssignableFrom(t.getClass())) {
            return t;
        }

        for (HandlerMethodArgumentResolver argumentResolver : argumentResolvers) {
            if (argumentResolver.supportsParameter(methodParameter)) {
                return argumentResolver.resolveArgument(methodParameter, request, response);
            }
        }

        throw new IllegalStateException("No suitable resolver for argument: " + methodParameter.getType());
    }
}
