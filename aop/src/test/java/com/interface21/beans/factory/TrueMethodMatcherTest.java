package com.interface21.beans.factory;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.assertThat;

class TrueMethodMatcherTest {

    @Test
    @DisplayName("TrueMethodMatcher 는 matches 시 true 만 반환한다.")
    void trueMethodMatcherMatchesTest() throws NoSuchMethodException {
        final MethodMatcher methodMatcher = TrueMethodMatcher.TRUE;

        final Class<FakeClass> targetClass = FakeClass.class;
        final Method targetClassMethod = targetClass.getMethod("testMethod");

        assertThat(methodMatcher.matches(targetClassMethod, targetClass)).isEqualTo(true);
    }

    static class FakeClass {

        public boolean testMethod() {
            return false;
        }

    }
}
