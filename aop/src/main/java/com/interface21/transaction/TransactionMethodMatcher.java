package com.interface21.transaction;

import com.interface21.beans.factory.MethodMatcher;
import com.interface21.transaction.annotation.Transactional;
import java.lang.reflect.Method;

public class TransactionMethodMatcher implements MethodMatcher {
    @Override
    public boolean matches(final Method m, final Class<?> targetClass, final Object[] args) {
        return hasTransactionalAnnotations(targetClass) || hasTransactionalAnnotations(m);
    }

    public boolean hasTransactionalAnnotations(final Class<?> targetClass) {
        return targetClass.getAnnotation(Transactional.class) != null;
    }

    public boolean hasTransactionalAnnotations(final Method method) {
        return method.getAnnotation(Transactional.class) != null;
    }
}
