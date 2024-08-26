package com.interface21.transaction.interceptor;

import com.interface21.beans.factory.ProxyFactoryBean;
import com.interface21.transaction.PlatformTransactionManager;
import com.interface21.transaction.annotation.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class TransactionProxyFactoryBeanTest {

    @Test
    @DisplayName("TransactionAdvisor 를 가진 프록시 객체를 생성할 수 있다.")
    void transactionAdvisorAddedTest() {
        final PlatformTransactionManager mockTransactionManager = mock(PlatformTransactionManager.class);
        final ProxyFactoryBean<FakeService> proxyFactoryBean = new ProxyFactoryBean<>(FakeService.class);

        final TransactionProxyFactoryBean<FakeService> factoryBean = new TransactionProxyFactoryBean<>(proxyFactoryBean, mockTransactionManager);
        final FakeService proxy = factoryBean.getObject();

        assertThat(proxy).isInstanceOf(FakeService.class);
    }

    @Test
    @DisplayName("프록시 객체가 정상적으로 트랜잭션 시작과 커밋을 처리한다.")
    void proxyTransactionCommitTest() {
        final PlatformTransactionManager mockTransactionManager = mock(PlatformTransactionManager.class);
        final ProxyFactoryBean<FakeService> proxyFactoryBean = new ProxyFactoryBean<>(FakeService.class);
        final TransactionProxyFactoryBean<FakeService> factoryBean = new TransactionProxyFactoryBean<>(proxyFactoryBean, mockTransactionManager);
        final FakeService proxy = factoryBean.getObject();

        proxy.testMethod();

        verify(mockTransactionManager, times(1)).begin();
        verify(mockTransactionManager, times(1)).commit();
    }

    @Test
    @DisplayName("프록시 객체가 예외 발생 시 트랜잭션을 롤백한다.")
    void proxyTransactionRollbackTest() {
        final PlatformTransactionManager mockTransactionManager = mock(PlatformTransactionManager.class);
        final ProxyFactoryBean<FakeService> proxyFactoryBean = new ProxyFactoryBean<>(FakeService.class);
        final TransactionProxyFactoryBean<FakeService> factoryBean = new TransactionProxyFactoryBean<>(proxyFactoryBean, mockTransactionManager);
        final FakeService proxy = factoryBean.getObject();

        try {
            proxy.testMethodWithException();
        } catch (final Exception ignored) {
        }

        verify(mockTransactionManager, times(1)).begin();
        verify(mockTransactionManager, times(1)).rollback();
    }

    @Transactional
    static class FakeService {
        public void testMethod() {
        }

        public void testMethodWithException() {
            throw new RuntimeException();
        }
    }
}
