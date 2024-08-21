package com.interface21.beans.factory.config;

import com.interface21.beans.factory.BeanFactory;
import com.interface21.transaction.PlatformTransactionManager;
import com.interface21.transaction.annotation.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TransactionBeanPostProcessorTest {

    private final BeanFactory beanFactory = mock(BeanFactory.class);
    private final PlatformTransactionManager transactionManager = mock(PlatformTransactionManager.class);

    @BeforeEach
    void setUp() {
        when(beanFactory.getBean(PlatformTransactionManager.class)).thenReturn(transactionManager);
    }

    @Test
    void transactional이_bean의_메소드에_하나라도_있는_경우_지원한다() {
        TransactionBeanPostProcessor beanPostProcessor = new TransactionBeanPostProcessor(beanFactory);
        boolean actual = beanPostProcessor.accept(new MethodTransactional());
        assertThat(actual).isTrue();
    }

    @Test
    void transactional이_bean의_클래스에_있는_경우_지원한다() {
        TransactionBeanPostProcessor beanPostProcessor = new TransactionBeanPostProcessor(beanFactory);
        boolean actual = beanPostProcessor.accept(new ClassTransactional());
        assertThat(actual).isTrue();
    }

    @Test
    void transactional이_하나도_없는_경우_지원하지_않는다() {
        TransactionBeanPostProcessor beanPostProcessor = new TransactionBeanPostProcessor(beanFactory);
        boolean actual = beanPostProcessor.accept(new NoTransactional());
        assertThat(actual).isFalse();
    }

    @Transactional
    private static class ClassTransactional {

        public String getName() {
            return "jinyoung";
        }
    }

    private static class MethodTransactional {

        @Transactional
        public String yesTransactional() {
            return "yes";
        }

        public String noTransactional() {
            return "no";
        }
    }

    private static class NoTransactional {

        public String getName() {
            return "jinyoung";
        }
    }
}
