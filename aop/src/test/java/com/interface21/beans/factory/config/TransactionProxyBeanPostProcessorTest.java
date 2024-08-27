package com.interface21.beans.factory.config;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.mock;

import com.interface21.beans.factory.ProxyFactoryBean;
import com.interface21.transaction.PlatformTransactionManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import sample.NonTransactionalService;
import sample.TransactionalClassService;
import sample.TransactionalMethodService;

class TransactionProxyBeanPostProcessorTest {

    private TransactionProxyBeanPostProcessor processor;
    private PlatformTransactionManager transactionManager;

    @BeforeEach
    public void setUp() {
        transactionManager = mock(PlatformTransactionManager.class);
        processor = new TransactionProxyBeanPostProcessor(transactionManager);
    }

    @Test
    @DisplayName("@Transactional 이 붙은 메서드에 트랜잭션 프록시를 생성한다.")
    void init_withTransactionalMethod() {
        //given
        TransactionalMethodService service = new TransactionalMethodService();

        //when
        Object result = processor.postInitialization(service);

        //then
        assertThat(result).isInstanceOf(ProxyFactoryBean.class);
    }

    @Test
    @DisplayName("@Transactional 이 붙은 클래스에 트랜잭션 프록시를 생성한다.")
    void init_withTransactionalClass() {
        //given
        TransactionalClassService service = new TransactionalClassService();

        //when
        Object result = processor.postInitialization(service);

        //then
        assertThat(result).isInstanceOf(ProxyFactoryBean.class);
    }

    @Test
    @DisplayName("@Transactional 이 붙지 않은 메서드는 트랜잭션 프록시를 생성하지 않는다.")
    void init_withOutTransactional() {
        //given
        NonTransactionalService service = new NonTransactionalService();

        //when
        Object result = processor.postInitialization(service);

        //then
        assertThat(result).isNotInstanceOf(ProxyFactoryBean.class);
    }
}
