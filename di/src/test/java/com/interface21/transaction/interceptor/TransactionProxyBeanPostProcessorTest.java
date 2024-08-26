package com.interface21.transaction.interceptor;

import com.interface21.beans.factory.BeanFactory;
import com.interface21.transaction.annotation.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class TransactionProxyBeanPostProcessorTest {

    private TransactionProxyBeanPostProcessor processor;

    @BeforeEach
    void setUp() {
        final BeanFactory beanFactory = mock(BeanFactory.class);
        processor = new TransactionProxyBeanPostProcessor(beanFactory);
    }

    @Test
    @DisplayName("@Transactional 이 붙은 클래스는 TransactionProxyFactoryBean 를 반환한다.")
    void createProxyForTransactionalAnnotatedClassTest() {
        final var bean = new TransactionalClass();

        final Object processedBean = processor.postInitialization(bean);

        assertThat(processedBean).isInstanceOf(TransactionProxyFactoryBean.class);
    }

    @Test
    @DisplayName("@Transactional 이 붙은 메서드를 가진 클래스는 TransactionProxyFactoryBean 를 반환한다.")
    void createProxyForTransactionalAnnotatedMethodTest() {
        final var bean = new ClassWithTransactionalMethod();

        final Object processedBean = processor.postInitialization(bean);

        assertThat(processedBean).isInstanceOf(TransactionProxyFactoryBean.class);
    }

    @Test
    @DisplayName("@Transactional 이 없는 클래스는 자기자신 그대로 반환한다.")
    void noProxyForNonTransactionalClassTest() {
        final var bean = new NonTransactionalClass();

        final Object processedBean = processor.postInitialization(bean);

        assertThat(processedBean).isSameAs(bean);
    }


    @Transactional
    static class TransactionalClass {
    }

    static class ClassWithTransactionalMethod {
        @Transactional
        public void test() {
        }
    }

    static class NonTransactionalClass {
        public void test() {
        }
    }
}
