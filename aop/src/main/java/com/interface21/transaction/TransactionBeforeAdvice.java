package com.interface21.transaction;

import com.interface21.framework.BeforeAdvice;
import com.interface21.framework.MethodInvocation;

public class TransactionBeforeAdvice implements BeforeAdvice {

    PlatformTransactionManager platformTransactionManager;

    public TransactionBeforeAdvice(PlatformTransactionManager platformTransactionManager) {
        this.platformTransactionManager = platformTransactionManager;
    }

    @Override
    public void invoke(MethodInvocation invocation) throws Throwable {
        platformTransactionManager.begin();
    }
}
