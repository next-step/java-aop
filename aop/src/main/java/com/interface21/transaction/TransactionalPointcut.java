package com.interface21.transaction;

import com.interface21.transaction.annotation.Transactional;
import java.lang.reflect.Method;

public class TransactionalPointcut implements TransactionalMatcher {

    @Override
    public boolean matches(Method method) {
        return method.isAnnotationPresent(Transactional.class) ||
            method.getDeclaringClass()
                .isAnnotationPresent(Transactional.class);
    }
}
