package com.interface21.beans.factory;

public interface BeanPostProcessor {
    Object postInitialization(Object bean);
}
