package com.interface21.transaction;

import com.interface21.framework.BeforeAdvice;
import com.interface21.framework.MethodInvocation;
import com.interface21.jdbc.datasource.DataSourceUtils;
import com.interface21.transaction.annotation.Transactional;
import java.lang.reflect.Method;

public class TransactionBeforeAdvice implements BeforeAdvice {

    PlatformTransactionManager platformTransactionManager;

    public TransactionBeforeAdvice(PlatformTransactionManager platformTransactionManager) {
        this.platformTransactionManager = platformTransactionManager;
    }

    @Override
    public void invoke(MethodInvocation invocation) throws Throwable {
        platformTransactionManager.begin();
        setReadOnly(invocation);
    }


    private void setReadOnly(MethodInvocation invocation) {
        Transactional transactional = getTransactional(invocation);
        if (transactional.readOnly()) {
            DataSourceUtils.setReadOnly(platformTransactionManager.getDataSource(), true);
        }
    }

    private Transactional getTransactional(MethodInvocation invocation) {
        Method method = invocation.getMethod();
        if (method.isAnnotationPresent(Transactional.class)) {
            return method.getAnnotation(Transactional.class);
        }
        return method.getDeclaringClass().getAnnotation(Transactional.class);
    }
}
