package com.interface21.webmvc.servlet.mvc;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ExceptionHandlerExecutionTest {

    private ExceptionHandlerExecution exceptionHandlerExecution;

    @BeforeEach
    void setUp() throws NoSuchMethodException {
        final Method method = FakeControllerAdvice.class.getMethod("handleRuntimeException", RuntimeException.class);
        exceptionHandlerExecution = new ExceptionHandlerExecution(new FakeControllerAdvice(), method);
    }

    @Test
    @DisplayName("예외를 핸들러가 정상적으로 실행된다.")
    void handleTest() {
        final String result = (String) exceptionHandlerExecution.handle(new RuntimeException("test"));

        assertThat(result).isEqualTo("예외발생: test");
    }

    @Test
    @DisplayName("핸들러 실행 중 예외 발생하면 IllegalStateException 로 변환해서 던진다.")
    void handleExceptionTest() throws NoSuchMethodException {
        final Method method = FakeControllerAdvice.class.getMethod("handleExceptionWithError", RuntimeException.class);
        exceptionHandlerExecution = new ExceptionHandlerExecution(new FakeControllerAdvice(), method);

        assertThatThrownBy(() -> exceptionHandlerExecution.handle(new RuntimeException()))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("cannot invoke ExceptionHandlerExecution");
    }

    static class FakeControllerAdvice {

        public String handleRuntimeException(final RuntimeException ex) {
            return "예외발생: " + ex.getMessage();
        }

        public String handleExceptionWithError(final RuntimeException ex) {
            throw new IllegalArgumentException("Error in handler");
        }
    }
}
