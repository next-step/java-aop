package com.interface21.transaction.interceptor;

import com.interface21.beans.factory.MethodMatcher;
import com.interface21.beans.factory.PointCut;
import com.interface21.transaction.annotation.Transactional;

public class TransactionPointcut implements PointCut {
    @Override
    public MethodMatcher getMethodMatcher() {
        return (method, targetClass, args) -> method.isAnnotationPresent(Transactional.class) || targetClass.isAnnotationPresent(Transactional.class);
    }
}
