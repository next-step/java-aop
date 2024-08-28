package com.interface21.webmvc.servlet.mvc.tobe;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import com.interface21.context.stereotype.ControllerAdvice;
import com.interface21.web.bind.annotation.ExceptionHandler;
import com.interface21.webmvc.servlet.ModelAndView;
import com.interface21.webmvc.servlet.view.JspView;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DefaultHandlerExceptionResolverTest {

    private DefaultHandlerExceptionResolver resolver;
    private Map<Class<? extends Throwable>, HandlerExecution> handlerExecutions;

    @BeforeEach
    void setUp() {
        Map<Class<?>, Object> controllerAdvices = new HashMap<>();
        controllerAdvices.put(SampleControllerAdvice.class, new SampleControllerAdvice());

        ExceptionHandlerConverter exceptionHandlerConverter = new ExceptionHandlerConverter();
        handlerExecutions = exceptionHandlerConverter.convert(controllerAdvices);
        resolver = new DefaultHandlerExceptionResolver(handlerExecutions);
    }

    @Test
    @DisplayName("예외 클래스에 대해 핸들러가 있는 경우, 핸들러를 통해 예외 처리를 진행한다.")
    void canResolveException() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        ModelAndView modelAndView = resolver.resolveException(request, response, new IllegalArgumentException());

        assertThat(modelAndView.getView()).isInstanceOf(JspView.class);
        assertThat(modelAndView.getView()).extracting("viewName").isEqualTo("error");
    }

    @Test
    @DisplayName("예외 클래스에 대해 핸들러가 없는 경우, null을 반환한다.")
    void cannotResolveException() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        ModelAndView modelAndView = resolver.resolveException(request, response, new NotHandledException());

        Assertions.assertThat(modelAndView).isNull();
    }

    @Test
    @DisplayName("상위 예외 클래스에 대해 핸들러가 있는 경우, 핸들러를 통해 예외 처리를 진행한다.")
    void canResolveExceptionWithSuperClass() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        ModelAndView modelAndView = resolver.resolveException(request, response, new SampleException());

        assertThat(modelAndView.getView()).isInstanceOf(JspView.class);
        assertThat(modelAndView.getView()).extracting("viewName").isEqualTo("super");
    }

    @ControllerAdvice
    static class SampleControllerAdvice {

        @ExceptionHandler({IllegalArgumentException.class})
        public ModelAndView handleIllegalArgumentException() {
            return new ModelAndView(new JspView("error"));
        }

        @ExceptionHandler(SuperSampleException.class)
        public ModelAndView handleSampleException() {
            return new ModelAndView(new JspView("super"));
        }
    }

    static class SuperSampleException extends RuntimeException {
    }

    static class SampleException extends SuperSampleException {
    }

    static class NotHandledException extends RuntimeException {

    }
}
