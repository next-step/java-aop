package com.interface21.webmvc.servlet.mvc;

import com.interface21.web.method.support.HandlerMethodArgumentResolver;
import com.interface21.core.MethodParameter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.lang.reflect.Method;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ExceptionHandlerExecutionTest {

    private ExceptionHandlerExecution exceptionHandlerExecution;
    private HttpServletRequest request;
    private HttpServletResponse response;

    @BeforeEach
    void setUp() throws NoSuchMethodException {
        request = Mockito.mock(HttpServletRequest.class);
        response = Mockito.mock(HttpServletResponse.class);

        final Method method = FakeControllerAdvice.class.getMethod("handleRuntimeException", RuntimeException.class);
        exceptionHandlerExecution = new ExceptionHandlerExecution(
                List.of(new FakeArgumentResolver()),
                new FakeControllerAdvice(),
                method
        );
    }

    @Test
    @DisplayName("예외를 핸들러가 정상적으로 실행된다.")
    void handleTest() {
        final String result = (String) exceptionHandlerExecution.handle(request, response, new RuntimeException("test"));

        assertThat(result).isEqualTo("예외발생: test");
    }

    @Test
    @DisplayName("핸들러 실행 중 예외 발생하면 IllegalStateException 로 변환해서 던진다.")
    void handleExceptionTest() throws NoSuchMethodException {
        final Method method = FakeControllerAdvice.class.getMethod("handleExceptionWithError", RuntimeException.class);
        exceptionHandlerExecution = new ExceptionHandlerExecution(
                List.of(new FakeArgumentResolver()),
                new FakeControllerAdvice(),
                method
        );

        assertThatThrownBy(() -> exceptionHandlerExecution.handle(request, response, new RuntimeException()))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("cannot invoke ExceptionHandlerExecution");
    }

    @Test
    @DisplayName("지원되지 않는 파라미터 타입이 있으면 IllegalStateException 이 발생한다.")
    void unsupportedParameterTypeTest() throws NoSuchMethodException {
        final Method method = FakeControllerAdvice.class.getMethod("handleWithUnsupportedParameter", Integer.class);
        exceptionHandlerExecution = new ExceptionHandlerExecution(
                List.of(new FakeArgumentResolver()),
                new FakeControllerAdvice(),
                method
        );

        assertThatThrownBy(() -> exceptionHandlerExecution.handle(request, response, new RuntimeException()))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("No suitable resolver for argument");
    }

    static class FakeControllerAdvice {

        public String handleRuntimeException(final RuntimeException ex) {
            return "예외발생: " + ex.getMessage();
        }

        public String handleExceptionWithError(final RuntimeException ex) {
            throw new IllegalArgumentException("Error in handler");
        }

        public String handleWithUnsupportedParameter(final Integer unsupportedParam) {
            return "Unsupported param: " + unsupportedParam;
        }
    }

    static class FakeArgumentResolver implements HandlerMethodArgumentResolver {

        @Override
        public boolean supportsParameter(final MethodParameter parameter) {
            return parameter.getType().equals(HttpServletRequest.class) || parameter.getType().equals(Throwable.class);
        }

        @Override
        public Object resolveArgument(final MethodParameter parameter, final HttpServletRequest request, final HttpServletResponse response) {
            if (parameter.getType().equals(HttpServletRequest.class)) {
                return request;
            } else if (parameter.getType().equals(Throwable.class)) {
                return new RuntimeException();
            }
            return null;
        }
    }
}
