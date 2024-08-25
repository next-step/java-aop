package com.interface21.transaction.aop;

import com.interface21.beans.factory.PointCut;
import com.interface21.transaction.annotation.Transactional;
import java.lang.reflect.Method;

public class TransactionalPointCut implements PointCut {

    @Override
    public boolean matches(Method method) {
        Class<?> declaringClass = method.getDeclaringClass();
        if (declaringClass.isAnnotationPresent(Transactional.class)) {
            return true;
        }

        return method.isAnnotationPresent(Transactional.class);
    }
}
