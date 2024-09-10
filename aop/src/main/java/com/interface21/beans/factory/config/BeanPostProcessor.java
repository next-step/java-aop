package com.interface21.beans.factory.config;

public interface BeanPostProcessor {

    Object postInitialization(Object bean);
}
