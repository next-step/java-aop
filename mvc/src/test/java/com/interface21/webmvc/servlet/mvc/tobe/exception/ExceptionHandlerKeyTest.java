package com.interface21.webmvc.servlet.mvc.tobe.exception;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ExceptionHandlerKeyTest {

    @Test
    void Exception이_동일하면_true를_반환한다() {
        ExceptionHandlerKey exceptionHandlerKey = new ExceptionHandlerKey(IllegalArgumentException.class);
        boolean actual = exceptionHandlerKey.isMatch(new IllegalArgumentException());
        assertThat(actual).isTrue();
    }

    @Test
    void Exception_type이_다르면_false를_반환한다() {
        ExceptionHandlerKey exceptionHandlerKey = new ExceptionHandlerKey(IllegalStateException.class);
        boolean actual = exceptionHandlerKey.isMatch(new IllegalArgumentException());
        assertThat(actual).isFalse();
    }
}
