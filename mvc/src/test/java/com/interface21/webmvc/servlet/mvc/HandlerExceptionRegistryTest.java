package com.interface21.webmvc.servlet.mvc;

import com.interface21.webmvc.servlet.ModelAndView;
import com.interface21.webmvc.servlet.view.JspView;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;

class HandlerExceptionRegistryTest {

    private HandlerExceptionRegistry exceptionRegistry;
    private HttpServletRequest request;
    private HttpServletResponse response;

    @BeforeEach
    void setUp() {
        exceptionRegistry = new HandlerExceptionRegistry();
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);

        exceptionRegistry.addResolver(new RuntimeExceptionResolver());
    }

    @Test
    @DisplayName("지원하는 예외에 대해 적절한 예외 처리기를 사용하여 예외를 처리한다.")
    void resolveSupportedExceptionTest() throws Throwable {
        final Throwable exception = new RuntimeException();

        final ModelAndView mav = exceptionRegistry.resolve(request, response, exception);

        assertThat(mav.getView()).isEqualTo(new JspView("redirect:/runtime.jsp"));
    }

    @Test
    @DisplayName("지원하지 않는 예외가 발생했을 때 해당 예외를 던진다.")
    void resolveUnsupportedExceptionTest() {
        final Throwable exception = new Exception();

        assertThatThrownBy(() -> exceptionRegistry.resolve(request, response, exception))
                .isInstanceOf(Exception.class);
    }

    static class RuntimeExceptionResolver implements HandlerExceptionResolver {

        @Override
        public ModelAndView resolveException(final HttpServletRequest request, final HttpServletResponse response, final Throwable throwable) {
            return new ModelAndView(new JspView("redirect:/runtime.jsp"));
        }

        @Override
        public boolean supports(final Throwable throwable) {
            return throwable instanceof RuntimeException;
        }

        @Override
        public void addAll(final Map<Class<? extends Throwable>, ExceptionHandlerExecution> convert) {

        }
    }

}
