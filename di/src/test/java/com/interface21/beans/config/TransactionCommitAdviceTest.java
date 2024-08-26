package com.interface21.beans.config;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import samples.FakeTransactionManager;
import samples.TransactionState;

import static org.assertj.core.api.Assertions.assertThat;

class TransactionCommitAdviceTest {

    private final FakeTransactionManager transactionManager = new FakeTransactionManager();
    private final TransactionCommitAdvice transactionCommitAdvice = new TransactionCommitAdvice(transactionManager);

    @DisplayName("advice 가 실행 되면 트랜잭션이 커밋 된다")
    @Test
    public void commitAdvice() throws Throwable {
        // when
        transactionCommitAdvice.afterReturning(null,null, null, null);

        // then
        assertThat(transactionManager.getState()).isEqualTo(TransactionState.COMMIT);
    }
}
