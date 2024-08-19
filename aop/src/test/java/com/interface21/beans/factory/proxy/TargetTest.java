package com.interface21.beans.factory.proxy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TargetTest {

    @DisplayName("프록시로 생성할 객체의 Class 를 갖는 Target 클래스를 생성 한다")
    @Test
    public void getTargetClass() throws Exception {
        // given
        final Target<TestTarget> testTargetTarget = new Target<TestTarget>(TestTarget.class);

        // when
        final Class<?> actual = testTargetTarget.getTargetClass();

        // then
        assertThat(actual).isEqualTo(TestTarget.class);
    }

    private static class TestTarget {

    }
}
