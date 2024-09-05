package com.interface21.beans.factory.config;

public interface BeanPostProcessor {
    boolean requiresProcessing(Object bean);

    Object postInitialization(Object bean);
}
