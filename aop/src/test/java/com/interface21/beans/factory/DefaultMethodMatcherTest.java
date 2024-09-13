package com.interface21.beans.factory;

import static org.junit.jupiter.api.Assertions.assertTrue;

import com.interface21.beans.factory.proxy.pointcut.DefaultMethodMatcher;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class DefaultMethodMatcherTest {
    @Test
    @DisplayName("매칭 되는 Method가 주어졌을때, Pointcut은 true 값을 반환합니다.")
    void testMatchingMethodMatcher() throws NoSuchMethodException {
        DefaultMethodMatcher methodMatcher = new DefaultMethodMatcher("hi");

        assertTrue(methodMatcher.matches(Sample.class.getMethod("hi"), Sample.class));
    }

    @Test
    @DisplayName("매칭 되지 않는는 Method가 주어졌을때, Pointcut은 true 값을 반환합니다.")
    void testUnmatchingMethodMatcher() throws NoSuchMethodException {
        DefaultMethodMatcher methodMatcher = new DefaultMethodMatcher("hi");

        assertTrue(methodMatcher.matches(Sample.class.getMethod("hi"), Sample.class));
    }
}
