package com.interface21.webmvc.servlet.mvc;

import com.interface21.core.MethodParameter;
import com.interface21.web.method.support.HandlerMethodArgumentResolver;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.IntStream;

public class ExceptionHandlerExecution {
    private static final Map<Method, MethodParameter[]> methodParameterCache = new ConcurrentHashMap<>();

    private final List<HandlerMethodArgumentResolver> argumentResolvers;
    private final Object declaredObject;
    private final Method method;

    public ExceptionHandlerExecution(final List<HandlerMethodArgumentResolver> argumentResolvers, final Object declaredObject, final Method method) {
        this.argumentResolvers = argumentResolvers;
        this.declaredObject = declaredObject;
        this.method = method;
    }

    public Object handle(final HttpServletRequest request, final HttpServletResponse response, final Throwable throwable) {
        final Object[] params = populateArguments(request, response, throwable);
        try {
            return method.invoke(declaredObject, params);
        } catch (final IllegalAccessException | InvocationTargetException e) {
            throw new IllegalStateException("cannot invoke ExceptionHandlerExecution", e);
        }
    }

    private Object[] populateArguments(final HttpServletRequest request, final HttpServletResponse response, final Throwable throwable) {
        return Arrays.stream(getMethodParameters())
                .map(param -> resolveArgument(param, request, response, throwable))
                .toArray();
    }

    private MethodParameter[] getMethodParameters() {
        return methodParameterCache.computeIfAbsent(method, this::createMethodParameters);
    }

    private MethodParameter[] createMethodParameters(final Method method) {
        final Class<?>[] parameterTypes = method.getParameterTypes();
        final Annotation[][] parameterAnnotations = method.getParameterAnnotations();
        final String[] parameterNames = Arrays.stream(method.getParameters())
                .map(Parameter::getName)
                .toArray(String[]::new);

        return IntStream.range(0, parameterTypes.length)
                .mapToObj(i -> new MethodParameter(method, parameterTypes[i], parameterAnnotations[i], parameterNames[i]))
                .toArray(MethodParameter[]::new);
    }

    private Object resolveArgument(final MethodParameter methodParameter, final HttpServletRequest request, final HttpServletResponse response, final Throwable throwable) {
        if (Throwable.class.isAssignableFrom(methodParameter.getType())) {
            return throwable;
        }

        return argumentResolvers.stream()
                .filter(resolver -> resolver.supportsParameter(methodParameter))
                .findFirst()
                .map(resolver -> resolver.resolveArgument(methodParameter, request, response))
                .orElseThrow(() -> new IllegalStateException("No suitable resolver for argument: " + methodParameter.getType()));
    }
}
