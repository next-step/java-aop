package com.interface21.transaction.aop;

import com.interface21.beans.factory.proxy.JoinPoint;
import com.interface21.dao.DataAccessException;
import com.interface21.transaction.PlatformTransactionManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

class TransactionAdviceTest {

    private final PlatformTransactionManager transactionManager = mock(PlatformTransactionManager.class);
    private final JoinPoint joinPoint = mock(JoinPoint.class);

    @BeforeEach
    void setUp() {
        doNothing().when(transactionManager).begin();
        doNothing().when(transactionManager).rollback();
        doNothing().when(transactionManager).commit();
    }

    @Test
    void Transaction_실행_시_예외가_발생하면_rollback한다() throws Throwable {
        TransactionAdvice transactionAdvice = new TransactionAdvice(transactionManager);
        when(joinPoint.proceed()).thenThrow(new RuntimeException("예외발생"));

        assertThatThrownBy(() -> transactionAdvice.invoke(joinPoint))
                .isInstanceOf(DataAccessException.class);

        InOrder inOrder = Mockito.inOrder(joinPoint, transactionManager);
        inOrder.verify(transactionManager).begin();
        inOrder.verify(joinPoint).proceed();
        inOrder.verify(transactionManager).rollback();
    }
}
