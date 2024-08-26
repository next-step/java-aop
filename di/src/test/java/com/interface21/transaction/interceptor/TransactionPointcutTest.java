package com.interface21.transaction.interceptor;

import com.interface21.beans.factory.MethodMatcher;
import com.interface21.transaction.annotation.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

class TransactionPointcutTest {

    @Test
    @DisplayName("클래스레벨에 @Transactional 이 있으면 모든 메서드가 true 를 반환한다.")
    void classMatcherTest() throws NoSuchMethodException {
        final TransactionPointcut pointcut = new TransactionPointcut();
        final MethodMatcher methodMatcher = pointcut.getMethodMatcher();
        final Method transactionalMethod = FakeTransactionalWithClass.class.getMethod("transactionalMethod");
        final Method nonTransactionalMethod = FakeTransactionalWithClass.class.getMethod("nonTransactionalMethod");

        assertSoftly(softly -> {
            assertThat(methodMatcher.matches(transactionalMethod, FakeTransactionalWithClass.class)).isTrue();
            assertThat(methodMatcher.matches(nonTransactionalMethod, FakeTransactionalWithClass.class)).isTrue();
        });
    }

    @Test
    @DisplayName("메서드레벨에 @Transactional 이 있으면 true 를 반환한다.")
    void methodMatcherTest() throws NoSuchMethodException {
        final TransactionPointcut pointcut = new TransactionPointcut();
        final MethodMatcher methodMatcher = pointcut.getMethodMatcher();
        final Method testMethod = FakeTransactionalWithMethod.class.getMethod("testMethod");

        assertThat(methodMatcher.matches(testMethod, FakeTransactionalWithMethod.class)).isTrue();
    }

    @Test
    @DisplayName("클래스나 메서드레벨에 @Transactional 이 잆으면 false 를 반환한다.")
    void nonTransactionalMethodMatcherTest() throws NoSuchMethodException {
        final TransactionPointcut pointcut = new TransactionPointcut();
        final MethodMatcher methodMatcher = pointcut.getMethodMatcher();
        final Method nonTransactionalMethod = FakeNonTransactional.class.getMethod("testMethod");

        final boolean matches = methodMatcher.matches(nonTransactionalMethod, FakeNonTransactional.class);

        assertThat(matches).isFalse();
    }

    @Transactional
    static class FakeTransactionalWithClass {
        public void transactionalMethod() {
        }

        public void nonTransactionalMethod() {
        }
    }

    static class FakeTransactionalWithMethod {
        @Transactional
        public void testMethod() {
        }
    }

    static class FakeNonTransactional {
        public void testMethod() {
        }
    }
}
