package com.interface21.beans.factory.proxy;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
class TargetTest {

    @Test
    void 실행할_클래스_타입_정보를_가진_객체를_생성한다() {
        Target<TargetClass> actual = new Target<>(TargetClass.class);
        assertThat(actual.getType()).isEqualTo(TargetClass.class);
    }

    public static class TargetClass {}
}
