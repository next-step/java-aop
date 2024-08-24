package com.interface21.transaction.aop;

import com.interface21.beans.factory.proxy.PointCut;
import com.interface21.transaction.annotation.Transactional;

import java.lang.reflect.Method;

public class TransactionalPointCut implements PointCut {

    @Override
    public boolean matches(Method method) {
        Class<?> declaringClass = method.getDeclaringClass();
        return declaringClass.isAnnotationPresent(Transactional.class)
                || method.isAnnotationPresent(Transactional.class);
    }
}
