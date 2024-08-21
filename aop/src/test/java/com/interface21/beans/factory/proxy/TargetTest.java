package com.interface21.beans.factory.proxy;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class TargetTest {

    @Test
    void 객체를_받아_Target을_생성한다() {
        TargetClass givenTarget = new TargetClass();
        Target<TargetClass> actual = new Target<>(givenTarget, new Object[0]);

        assertAll(
                () -> assertThat(actual.getType()).isEqualTo(TargetClass.class),
                () -> assertThat(actual.getTarget()).isEqualTo(givenTarget)
        );
    }

    public static class TargetClass {}
}
