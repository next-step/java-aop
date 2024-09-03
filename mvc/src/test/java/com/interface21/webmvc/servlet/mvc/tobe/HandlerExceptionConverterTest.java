package com.interface21.webmvc.servlet.mvc.tobe;

import com.interface21.webmvc.servlet.mvc.ExceptionHandler;
import com.interface21.webmvc.servlet.mvc.ExceptionHandlerExecution;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class HandlerExceptionConverterTest {

    @Test
    @DisplayName("ControllerAdvice 에 ExceptionHandler 메서드 정보를 통해 ExceptionHandlerExecution 를 만들어 반환할 수 있다.")
    void convertTest() {
        final HandlerExceptionConverter converter = new HandlerExceptionConverter();
        final Map<Class<?>, Object> controllerAdvices = new HashMap<>();
        controllerAdvices.put(FakeControllerAdvice.class, new FakeControllerAdvice());

        final Map<Class<? extends Throwable>, ExceptionHandlerExecution> handlers = converter.convert(controllerAdvices);

        assertThat(handlers.get(RuntimeException.class)).isNotNull();
    }

    static class FakeControllerAdvice {
        @ExceptionHandler(RuntimeException.class)
        public void handleRuntimeException(final RuntimeException ex) {
            // Handle exception
        }
    }
}
