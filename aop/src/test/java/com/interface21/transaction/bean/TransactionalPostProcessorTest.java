package com.interface21.transaction.bean;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import com.interface21.beans.factory.proxy.factory.ProxyFactoryBean;
import com.interface21.transaction.PlatformTransactionManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class TransactionalPostProcessorTest {

    private TransactionalPostProcessor transactionalPostProcessor;

    @Test
    @DisplayName("PostProcessor가 메서드 트랜잭션을 가진 Bean을 Proxy bean으로 생성한 bean을 생성합니다.")
    void initializeTransactionMethod() {
        transactionalPostProcessor = new TransactionalPostProcessor(
            mock(PlatformTransactionManager.class));
        TransactionClass sample = new TransactionClass();
        Object wrapped = transactionalPostProcessor.postInitialization(sample);

        assertThat(wrapped).isInstanceOf(ProxyFactoryBean.class);
    }

    @Test
    @DisplayName("PostProcessor가 클래스 트랜잭션을 가진 Bean을 Proxy bean으로 생성한 bean을 생성합니다.")
    void intializeTransactionClass() {
        transactionalPostProcessor = new TransactionalPostProcessor(
            mock(PlatformTransactionManager.class));
        TransactionMethodClass sample = new TransactionMethodClass();
        Object wrapped = transactionalPostProcessor.postInitialization(sample);

        assertThat(wrapped).isInstanceOf(ProxyFactoryBean.class);
    }

    @Test
    @DisplayName("PostProcessor가 클래스 트랜잭션을 가지고있지 않은 Bean을 Proxy bean으로 생성한 bean을 생성하지 않습니다.")
    void intializeNonTransactionClass() {
        transactionalPostProcessor = new TransactionalPostProcessor(
                mock(PlatformTransactionManager.class));
        NonTransactionClass sample = new NonTransactionClass();
        Object wrapped = transactionalPostProcessor.postInitialization(sample);

        assertThat(wrapped).isNotInstanceOf(ProxyFactoryBean.class);
    }
}
