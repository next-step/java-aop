package com.interface21.transaction.aop;

import com.interface21.beans.factory.proxy.JoinPoint;
import com.interface21.beans.factory.proxy.SimpleJoinPoint;
import com.interface21.dao.DataAccessException;
import com.interface21.transaction.PlatformTransactionManager;
import net.sf.cglib.proxy.MethodProxy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;

class TransactionAdviceTest {

    private final PlatformTransactionManager transactionManager = mock(PlatformTransactionManager.class);
    private final JoinPoint joinPoint = new SimpleJoinPoint(
            new Simple(),
            Simple.class.getMethod("say"),
            new Object[0],
            MethodProxy.create(Simple.class, Simple.class, "()Ljava/lang/String;", "say", "say")
    );

    TransactionAdviceTest() throws NoSuchMethodException {
    }

    @BeforeEach
    void setUp() {
        doNothing().when(transactionManager).begin();
        doNothing().when(transactionManager).rollback();
        doNothing().when(transactionManager).commit();
    }

    @Test
    void Transaction_실행_시_예외가_발생하면_rollback한다() {
        TransactionAdvice transactionAdvice = new TransactionAdvice(transactionManager);

        assertThatThrownBy(() -> transactionAdvice.invoke(joinPoint))
                .isInstanceOf(DataAccessException.class);

        InOrder inOrder = Mockito.inOrder(transactionManager);
        inOrder.verify(transactionManager).begin();
        inOrder.verify(transactionManager).rollback();
    }

    private static class Simple {
        public String say() {
            return "say";
        }
    }
}
