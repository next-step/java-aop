package com.interface21.beans.factory.config;

import com.interface21.beans.factory.FactoryBean;

public class FactoryBeanPostProcessor implements BeanPostProcessor {
    @Override
    public boolean accept(Object bean) {
        return bean instanceof FactoryBean<?>;
    }

    @Override
    public Object postInitialization(Object bean) {
        return ((FactoryBean<?>) bean).getObject();
    }
}
