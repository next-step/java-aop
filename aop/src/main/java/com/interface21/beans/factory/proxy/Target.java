package com.interface21.beans.factory.proxy;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Arrays;

public class Target<T> {

    private final Class<T> type;
    private final T target;
    private final Class<?>[] constructorParameterTypes;

    public Target(T target, Constructor<?> constructor) {
        this.type = (Class<T>) target.getClass();
        this.target = target;
        this.constructorParameterTypes = constructor.getParameterTypes();
    }

    public Class<T> getType() {
        return type;
    }

    public T getTarget() {
        return target;
    }

    public Class<?>[] getConstructorParameterTypes() {
        return constructorParameterTypes;
    }

    // TODO 테스트
    public Object[] getConstructorParameters() {
        return Arrays.stream(constructorParameterTypes)
                .map(this::parseFieldByParameterType)
                .map(this::getFieldValue)
                .toArray();
    }

    private Field parseFieldByParameterType(Class<?> parameterType) {
        return Arrays.stream(type.getDeclaredFields())
                .filter(field -> field.getType().equals(parameterType))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 파라미터 필드입니다"));
    }

    private Object getFieldValue(Field field) {
        field.setAccessible(true);
        try {
            return field.get(target);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
