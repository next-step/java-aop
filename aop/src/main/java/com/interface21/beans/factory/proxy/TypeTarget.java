package com.interface21.beans.factory.proxy;

public class TypeTarget<T> implements Target {

    private final Class<T> targetClass;

    public TypeTarget(final Class<T> targetClass) {
        this.targetClass = targetClass;
    }

    public Class<T> getTargetClass() {
        return this.targetClass;
    }
}
