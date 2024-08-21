package com.interface21.beans.factory.proxy;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class TargetTest {

    @Test
    void 객체를_받아_Target을_생성한다() throws NoSuchMethodException {
        TargetClass givenTarget = new TargetClass();
        Target<TargetClass> actual = new Target<>(givenTarget, TargetClass.class.getConstructor());

        assertAll(
                () -> assertThat(actual.getType()).isEqualTo(TargetClass.class),
                () -> assertThat(actual.getTarget()).isEqualTo(givenTarget)
        );
    }

    @Test
    void constructor에_들어간_파라미터객체를_추출할_수_있다() {

    }

    public static class TargetClass {}
}
