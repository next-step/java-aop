package com.interface21.tx.aop;

import com.interface21.aop.advisor.Pointcut;
import com.interface21.tx.annotation.Transactional;

import java.lang.reflect.Method;

public class TransactionalPointcut implements Pointcut {
    @Override
    public boolean matches(Method method, Class<?> targetClass) {
        return targetClass.isAnnotationPresent(Transactional.class) ||
                method.isAnnotationPresent(Transactional.class);
    }
}
