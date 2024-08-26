package com.interface21.beans.config;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import samples.FakeTransactionManager;
import samples.TransactionState;

import static org.assertj.core.api.Assertions.assertThat;

class TransactionRollbackAdviceTest {

    private final FakeTransactionManager transactionManager = new FakeTransactionManager();
    private final TransactionRollbackAdvice transactionRollbackAdvice = new TransactionRollbackAdvice(transactionManager);

    @DisplayName("advice 가 실행 되면 트랜잭션이 롤백 된다")
    @Test
    public void rollbackAdvice() throws Throwable {
        // when
        transactionRollbackAdvice.afterThrowing(new RuntimeException());

        // then
        assertThat(transactionManager.getState()).isEqualTo(TransactionState.ROLLBACK);
    }
}
