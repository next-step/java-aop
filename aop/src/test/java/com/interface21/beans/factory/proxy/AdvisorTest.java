package com.interface21.beans.factory.proxy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class AdvisorTest {

    @DisplayName("Pointcut 이 없이 생성 되면 TruePointcut 을 기본으로 갖는다")
    @Test
    public void createWithTruePointcut() throws Exception {
        // given
        final Advice advice = new CustomAdvice();

        // when
        final Advisor actual = new Advisor(advice);

        // then
        assertThat(actual.getPointcut()).isEqualTo(Pointcut.TRUE);
    }

    @DisplayName("Pointcut 과 Advice 를 주입 받아 객체를 생성 한다")
    @Test
    public void createWithCustomPointcut() throws Exception {
        // given
        final Advice advice = new CustomAdvice();
        final Pointcut pointcut = new CustomPointcut();

        // when
        final Advisor actual = new Advisor(advice, pointcut);

        // then
        assertAll(
                () -> assertThat(actual.getAdvice()).isInstanceOf(CustomAdvice.class),
                () -> assertThat(actual.getPointcut()).isInstanceOf(CustomPointcut.class)
        );
    }

    private static class CustomPointcut implements Pointcut {
        @Override
        public boolean matches(final Method method, final Class<?> targetClass) {
            return false;
        }
    }

    private static class CustomAdvice implements Advice {
    }
}
