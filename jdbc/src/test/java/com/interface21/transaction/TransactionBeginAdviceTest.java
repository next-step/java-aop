package com.interface21.transaction;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import samples.FakeTransactionManager;
import samples.TransactionState;

class TransactionBeginAdviceTest {

    private final FakeTransactionManager transactionManager = new FakeTransactionManager();
    private final TransactionBeginAdvice transactionBeginAdvice = new TransactionBeginAdvice(transactionManager);

    @DisplayName("advice 가 실행 되면 트랜잭션이 시작 된다")
    @Test
    public void beginAdvice() throws Throwable {
        // when
        transactionBeginAdvice.invoke(null, null, null);

        // then
        Assertions.assertThat(transactionManager.getState()).isEqualTo(TransactionState.BEGIN);
    }
}
