package com.interface21.beans.factory;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TruePointcutTest {

    @Test
    @DisplayName("TruePointcut 은 항상 동일한 MethodMatcher.TRUE 를 반환한다.")
    void truePointcutMethodMatcherTest() {
        final PointCut pointCut = TruePointcut.TRUE;

        assertThat(pointCut.getMethodMatcher()).isSameAs(MethodMatcher.TRUE);
    }
}
