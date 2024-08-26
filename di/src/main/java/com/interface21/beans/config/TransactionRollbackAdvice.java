package com.interface21.beans.config;

import com.interface21.beans.factory.proxy.ThrowsAdvice;
import com.interface21.transaction.PlatformTransactionManager;

public class TransactionRollbackAdvice implements ThrowsAdvice {

    private final PlatformTransactionManager transactionManager;

    public TransactionRollbackAdvice(final PlatformTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    @Override
    public void afterThrowing(final Exception ex) {
        transactionManager.rollback();
    }
}
