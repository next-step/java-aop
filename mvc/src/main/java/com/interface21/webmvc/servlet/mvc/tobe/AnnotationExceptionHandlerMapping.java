package com.interface21.webmvc.servlet.mvc.tobe;

import com.interface21.beans.BeanUtils;
import org.reflections.ReflectionUtils;
import org.reflections.Reflections;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnnotationExceptionHandlerMapping implements ExceptionHandlerMapping {
    private final Map<Class<? extends Throwable>, HandlerExecution> handlerExecutions = new HashMap<>();

    @Override
    public void initialize() {
        List<Method> exceptionHandlers = getExceptionHandleMethods();
        for (Method method : exceptionHandlers) {
            Class<? extends Throwable> exception = getException(method);
            HandlerExecution handlerExecution = createHandlerExecution(method);
            handlerExecutions.put(exception, handlerExecution);
        }
    }

    private static HandlerExecution createHandlerExecution(Method method) {
        Object declaringObject = BeanUtils.instantiate(method.getDeclaringClass());
        return new HandlerExecution(List.of(), declaringObject, method);
    }

    private Class<? extends Throwable> getException(Method method) {
        return method.getAnnotation(ExceptionHandler.class).value();
    }

    private List<Method> getExceptionHandleMethods() {
        Reflections reflections = new Reflections("camp.nextstep"); // XXX 하드코딩
        return reflections.getTypesAnnotatedWith(ControllerAdvice.class).stream()
                .flatMap(type -> Arrays.stream(type.getDeclaredMethods())
                        .filter(method -> method.isAnnotationPresent(ExceptionHandler.class))
                ).toList();
    }

    @Override
    public Object getHandler(Throwable exception) {
        // exception 의 상속구조를 확인해서, 하나하나씩 올라가면서 확인한다
        Class<? extends Throwable> exceptionClass = exception.getClass();
        if (handlerExecutions.containsKey(exceptionClass)) {
            return handlerExecutions.get(exceptionClass);
        }

        for (Class<?> aClass : ReflectionUtils.getAllSuperTypes(exceptionClass)) {
            if (handlerExecutions.containsKey(aClass)) {
                return handlerExecutions.get(aClass);
            }
        }
        return null;
    }
}
