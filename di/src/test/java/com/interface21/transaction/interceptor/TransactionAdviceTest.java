package com.interface21.transaction.interceptor;

import com.interface21.transaction.PlatformTransactionManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class TransactionAdviceTest {

    private PlatformTransactionManager fakeTransactionManager;
    private TransactionAdvice transactionAdvice;

    @BeforeEach
    void setUp() {
        fakeTransactionManager = mock(PlatformTransactionManager.class);
        transactionAdvice = new TransactionAdvice(fakeTransactionManager);
    }

    @Test
    @DisplayName("메서드 실행 전 트랜잭션이 시작되어야 한다")
    void beforeTest() throws Throwable {
        final Method testMethod = FakeService.class.getMethod("testMethod");
        final Object[] args = new Object[]{};

        transactionAdvice.before(testMethod, args, new FakeService());

        verify(fakeTransactionManager, times(1)).begin();
    }

    @Test
    @DisplayName("메서드 정상 실행 후 트랜잭션이 커밋되어야 한다")
    void afterReturningTest() throws Throwable {
        final Method testMethod = FakeService.class.getMethod("testMethod");
        final Object[] args = new Object[]{};

        transactionAdvice.afterReturning(null, testMethod, args, new FakeService());

        verify(fakeTransactionManager, times(1)).commit();
    }

    @Test
    @DisplayName("메서드 실행 중 예외가 발생하면 트랜잭션이 롤백 되어야 한다")
    void afterThrowingTest() throws Throwable {
        final Method testMethod = FakeService.class.getMethod("testMethod");
        final Object[] args = new Object[]{};
        final Exception ex = new RuntimeException();

        transactionAdvice.afterThrowing(ex, testMethod, args, new FakeService());

        verify(fakeTransactionManager, times(1)).rollback();
    }

    static class FakeService {
        public void testMethod() {
        }
    }
}
