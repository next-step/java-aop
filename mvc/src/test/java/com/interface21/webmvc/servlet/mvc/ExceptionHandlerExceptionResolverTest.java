package com.interface21.webmvc.servlet.mvc;

import com.interface21.core.MethodParameter;
import com.interface21.web.method.support.HandlerMethodArgumentResolver;
import com.interface21.webmvc.servlet.ModelAndView;
import com.interface21.webmvc.servlet.view.JspView;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class ExceptionHandlerExceptionResolverTest {

    private ExceptionHandlerExceptionResolver exceptionResolver;

    @BeforeEach
    void setUp() throws NoSuchMethodException {
        final List<HandlerMethodArgumentResolver> argumentResolvers = List.of(new FakeArgumentResolver());

        final Method runtimeExceptionHandlerMethod = FakeControllerAdvice.class.getMethod("handleRuntimeException", RuntimeException.class);
        final ExceptionHandlerExecution runtimeExceptionHandlerExecution = new ExceptionHandlerExecution(argumentResolvers,new FakeControllerAdvice(), runtimeExceptionHandlerMethod);

        final Method illegalArgumentExceptionHandlerMethod = FakeControllerAdvice.class.getMethod("handleIllegalArgumentException", IllegalArgumentException.class);
        final ExceptionHandlerExecution illegalArgumentExceptionHandlerExecution = new ExceptionHandlerExecution(argumentResolvers, new FakeControllerAdvice(), illegalArgumentExceptionHandlerMethod);

        exceptionResolver = new ExceptionHandlerExceptionResolver(Map.of(
                RuntimeException.class, runtimeExceptionHandlerExecution,
                IllegalArgumentException.class, illegalArgumentExceptionHandlerExecution
        ));
    }

    @Test
    @DisplayName("RuntimeException 을 올바르게 처리한다.")
    void resolveExceptionTest() {
        final ModelAndView mav = exceptionResolver.resolveException(null, null, new RuntimeException());

        assertThat(mav.getView()).isEqualTo(new JspView("redirect:/runtime.jsp"));
    }

    @Test
    @DisplayName("등록된 IllegalArgumentException 을 올바르게 처리한다.")
    void resolveIllegalArgumentExceptionTest() {
        final ModelAndView mav = exceptionResolver.resolveException(null, null, new IllegalArgumentException());

        assertThat(mav.getView()).isEqualTo(new JspView("redirect:/index.jsp"));
    }

    @Test
    @DisplayName("등록되지 않은 IllegalStateException 을 상위 클래스인 RuntimeException 으로 올바르게 처리한다.")
    void resolveIllegalStateExceptionTest() {
        final ModelAndView mav = exceptionResolver.resolveException(null, null, new IllegalStateException());

        assertThat(mav.getView()).isEqualTo(new JspView("redirect:/runtime.jsp"));
    }

    @Test
    @DisplayName("등록되지 않은 예외에 대해 null 을 반환한다.")
    void resolveNotRegisteredExceptionTest() {
        final ModelAndView mav = exceptionResolver.resolveException(null, null, new Exception());

        assertThat(mav).isNull();
    }

    @Test
    @DisplayName("지원되는 예외 여부를 확인할 수 있다.")
    void supportsTest() {
        final boolean supports = exceptionResolver.supports(new RuntimeException());

        assertThat(supports).isTrue();
    }

    static class FakeControllerAdvice {
        public ModelAndView handleRuntimeException(final RuntimeException ex) {
            return new ModelAndView(new JspView("redirect:/runtime.jsp"));
        }

        public ModelAndView handleIllegalArgumentException(final IllegalArgumentException ex) {
            return new ModelAndView(new JspView("redirect:/index.jsp"));
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
