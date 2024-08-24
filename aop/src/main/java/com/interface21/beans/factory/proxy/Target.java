package com.interface21.beans.factory.proxy;

public class Target<T> {

    private final Class<T> targetClass;

    public Target(final Class<T> targetClass) {
        this.targetClass = targetClass;
    }

    public Class<T> getTargetClass() {
        return this.targetClass;
    }
}
