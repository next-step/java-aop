package com.interface21.beans.factory.config;

import com.interface21.transaction.PlatformTransactionManager;
import com.interface21.transaction.annotation.Transactional;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class TransactionBeanPostProcessorTest {

    private final PlatformTransactionManager transactionManager = mock(PlatformTransactionManager.class);

    @Test
    void transactional이_bean의_메소드에_하나라도_있는_경우_지원한다() {
        TransactionBeanPostProcessor beanPostProcessor = new TransactionBeanPostProcessor(transactionManager);
        boolean actual = beanPostProcessor.accept(new MethodTransactional());
        assertThat(actual).isTrue();
    }

    @Test
    void transactional이_bean의_클래스에_있는_경우_지원한다() {
        TransactionBeanPostProcessor beanPostProcessor = new TransactionBeanPostProcessor(transactionManager);
        boolean actual = beanPostProcessor.accept(new ClassTransactional());
        assertThat(actual).isTrue();
    }

    @Test
    void transactional이_하나도_없는_경우_지원하지_않는다() {
        TransactionBeanPostProcessor beanPostProcessor = new TransactionBeanPostProcessor(transactionManager);
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
