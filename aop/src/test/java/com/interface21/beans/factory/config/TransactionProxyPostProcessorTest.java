package com.interface21.beans.factory.config;

import com.interface21.beans.factory.ProxyFactoryBean;
import com.interface21.transaction.PlatformTransactionManager;
import com.interface21.transaction.annotation.Transactional;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;

@DisplayName("TransactionProxyPostProcessor 테스트")
class TransactionProxyPostProcessorTest {

    private TransactionProxyPostProcessor processor;
    private PlatformTransactionManager transactionManager;

    @BeforeEach
    public void setUp() {
        transactionManager = mock(PlatformTransactionManager.class);
        processor = new TransactionProxyPostProcessor(transactionManager);

        doNothing().when(transactionManager).begin();
        doNothing().when(transactionManager).commit();
        doNothing().when(transactionManager).rollback();
    }

    @DisplayName("트랜잭션어노테이션이 메서드의 포함된 경우 프록시 생성")
    @Test
    void testPostInitialization_TransactionalMethod() {
        TransactionalMethodService transactionalMethodService = new TransactionalMethodService();

        Object service = processor.postInitialization(transactionalMethodService);

        assertTrue(service instanceof ProxyFactoryBean);
    }

    @DisplayName("트랜잭션어노테이션이 클래스에 포함된 경우 프록시 생성")
    @Test
    void testPostInitialization_TransactionalClass() {
        TransactionalService transactionalService = new TransactionalService();

        Object service = processor.postInitialization(transactionalService);

        assertTrue(service instanceof ProxyFactoryBean);
    }

    @DisplayName("트랜잭션어노테이션이 포함되지 않은 경우 프록시 생성하지 않음")
    @Test
    void testPostInitialization_NonTransactionalClass() {
        NonTransactionalService nonTransactionalService = new NonTransactionalService();

        Object service = processor.postInitialization(nonTransactionalService);

        assertFalse(service instanceof ProxyFactoryBean);
    }

    @Transactional
    public static class TransactionalService {
        public void performTransaction() {
        }
    }

    public static class TransactionalMethodService {
        @Transactional
        public void performAction() {
        }
    }

    public static class NonTransactionalService {
        public void performAction() {

        }
    }
}
