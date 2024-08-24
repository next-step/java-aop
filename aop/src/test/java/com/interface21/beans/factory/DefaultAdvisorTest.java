package com.interface21.beans.factory;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;


class DefaultAdvisorTest {

    @Test
    @DisplayName("DefaultAdvisor 를 생성할 수 있다.")
    void advisorCreationTest() {
        final Advice advice = new FakeAdvice();
        final PointCut pointCut = new FakePointCut();
        final Advisor advisor = new DefaultAdvisor(advice, pointCut);

        assertSoftly(softly -> {
            softly.assertThat(advisor.getAdvice()).isEqualTo(advice);
            softly.assertThat(advisor.getPointCut()).isEqualTo(pointCut);
        });
    }

    @Test
    @DisplayName("DefaultAdvisor 가 Pointcut 을 받지 않으면 TruePointCut 을 사용한다.")
    void getDefaultPointCutTest() {
        final Advice advice = new FakeAdvice();
        final Advisor advisor = new DefaultAdvisor(advice);

        assertThat(advisor.getPointCut()).isEqualTo(PointCut.TRUE);
    }

    static class FakeAdvice implements Advice {
    }

    static class FakePointCut implements PointCut {
        @Override
        public MethodMatcher getMethodMatcher() {
            return new FakeMethodMatcher();
        }
    }

    static class FakeMethodMatcher implements MethodMatcher {
        @Override
        public boolean matches(final Method method, final Class<?> targetClass, final Object... args) {
            return false;
        }
    }
}
