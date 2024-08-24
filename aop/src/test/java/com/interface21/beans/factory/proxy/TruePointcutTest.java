package com.interface21.beans.factory.proxy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class TruePointcutTest {

    @DisplayName("matches 메서드의 반환 값으로 항상 true 를 반환 한다")
    @Test
    public void matchesTrue() throws Exception {
        // given
        final TruePointcut truePointcut = TruePointcut.getInstance();

        // when
        final boolean actual = truePointcut.matches(mock(), Object.class);

        // then
        assertThat(actual).isTrue();
    }

}
