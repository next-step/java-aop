package com.interface21.transaction;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import samples.FakeTransactionManager;
import samples.TransactionState;

class TransactionCommitAdviceTest {

    private final FakeTransactionManager transactionManager = new FakeTransactionManager();
    private final TransactionCommitAdvice transactionCommitAdvice = new TransactionCommitAdvice(transactionManager);

    @DisplayName("advice 가 실행 되면 트랜잭션이 커밋 된다")
    @Test
    public void commitAdvice() throws Throwable {
        // when
        transactionCommitAdvice.afterReturning(null,null, null, null);

        // then
        Assertions.assertThat(transactionManager.getState()).isEqualTo(TransactionState.COMMIT);
    }
}
