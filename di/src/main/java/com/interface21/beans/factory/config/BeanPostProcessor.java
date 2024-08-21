package com.interface21.beans.factory.config;

public interface BeanPostProcessor {

    boolean accept(Object bean);

    Object postInitialization(Object bean);
}
