package com.interface21.transaction;

import static org.assertj.core.api.Assertions.assertThat;

import com.interface21.transaction.annotation.Transactional;
import java.lang.reflect.Method;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class TransactionMethodMatcherTest {
    @Transactional
    private static class TransactionalTarget {
        public void nonTransactionalMethod() {}
        @Transactional
        public void transactionalMethod() {}
    }

    @DisplayName("targetClass와 method 모두 @Transactional을 가지고 있는 경우 true를 반환한다.")
    @Test
    void matches() throws NoSuchMethodException {
        Class<TransactionalTarget> targetClass = TransactionalTarget.class;
        Method nonTransactionalMethod = targetClass.getMethod("transactionalMethod");
        TransactionMethodMatcher transactionMethodMatcher = new TransactionMethodMatcher();

        boolean actual = transactionMethodMatcher.matches(nonTransactionalMethod, targetClass, new Object[]{});

        assertThat(actual).isTrue();
    }

    @DisplayName("targetClass만 @Transactional을 가지고 있어도 true를 반환한다.")
    @Test
    void matches2() throws NoSuchMethodException {
        Class<TransactionalTarget> targetClass = TransactionalTarget.class;
        Method nonTransactionalMethod = targetClass.getMethod("nonTransactionalMethod");
        TransactionMethodMatcher transactionMethodMatcher = new TransactionMethodMatcher();

        boolean actual = transactionMethodMatcher.matches(nonTransactionalMethod, targetClass, new Object[]{});

        assertThat(actual).isTrue();
    }

    private static class NonTransactionalTarget {
        @Transactional
        public void transactionalMethod() {}
    }

    @DisplayName("method만 @Transactional을 가지고 있어도 true를 반환한다.")
    @Test
    void matches3() throws NoSuchMethodException {
        Class<NonTransactionalTarget> targetClass = NonTransactionalTarget.class;
        Method nonTransactionalMethod = targetClass.getMethod("transactionalMethod");
        TransactionMethodMatcher transactionMethodMatcher = new TransactionMethodMatcher();

        boolean actual = transactionMethodMatcher.matches(nonTransactionalMethod, targetClass, new Object[]{});

        assertThat(actual).isTrue();
    }
}