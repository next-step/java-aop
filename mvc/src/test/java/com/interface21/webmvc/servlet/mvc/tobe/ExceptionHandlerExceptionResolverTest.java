package com.interface21.webmvc.servlet.mvc.tobe;

import com.interface21.context.stereotype.ControllerAdvice;
import com.interface21.web.bind.annotation.ExceptionHandler;
import com.interface21.webmvc.servlet.ModelAndView;
import com.interface21.webmvc.servlet.view.JspView;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;

@DisplayName("ExceptionHandlerExceptionResolver 테스트")
class ExceptionHandlerExceptionResolverTest {

    private ExceptionHandlerExceptionResolver resolver;
    private Map<Class<? extends Throwable>, HandlerExecution> handlerExecutions;

    @BeforeEach
    public void setUp() {
        Map<Class<?>, Object> controllerAdvice = new HashMap<>();
        controllerAdvice.put(ControllerAdviceSample.class, new ControllerAdviceSample());
        controllerAdvice.put(ControllerAdviceSample1.class, new ControllerAdviceSample1());
        controllerAdvice.put(ControllerAdviceSample2.class, new ControllerAdviceSample2());

        ControllerAdviceConverter controllerAdviceConverter = new ControllerAdviceConverter();

        handlerExecutions = controllerAdviceConverter.convert(controllerAdvice);
        resolver = new ExceptionHandlerExceptionResolver(handlerExecutions);
    }

    @DisplayName("특정 예외 클래스에 대한 핸들러 실행 객체가 있는 경우, 해당 핸들러 실행 객체를 사용하여 예외 처리를 수행한다.")
    @Test
    void testResolveExceptionWithMatchingHandler() throws Exception {

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        // 예외 처리 호출
        ModelAndView modelAndView = resolver.resolveException(request, response, new NullPointerException());

        // 결과 검증
        assertEquals(modelAndView.getView(), new JspView("error"));
    }

    @DisplayName("특정 예외 클래스에 대한 핸들러 실행 객체가 없는 경우, null을 반환한다.")
    @Test
    void testResolveExceptionWithNoMatchingHandler() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        // 정의되지 않은 예외 발생
        Exception exception = new Exception();

        // 예외 처리 호출
        ModelAndView modelAndView = resolver.resolveException(request, response, exception);

        // 결과 검증: 핸들러가 없으므로 null이어야 함
        assertEquals(null, modelAndView);
    }

    @DisplayName("특정 예외 클래스의 상위 클래스에 대한 핸들러 실행 객체가 있는 경우, 해당 핸들러 실행 객체를 사용하여 예외 처리를 수행한다.")
    @Test
    void testResolveExceptionWithMatchingSuperHandler() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        // 예외 처리 호출
        ModelAndView modelAndView = resolver.resolveException(request, response, new IllegalArgumentException());

        // 결과 검증
        assertEquals(modelAndView.getView(), new JspView("error"));
    }

    @ControllerAdvice
    public static class ControllerAdviceSample {

        @ExceptionHandler(NullPointerException.class)
        public ModelAndView handleException(Exception e) {
            return new ModelAndView(new JspView("error"));
        }
    }

    @ControllerAdvice
    public static class ControllerAdviceSample1 {

        @ExceptionHandler(RuntimeException.class)
        public ModelAndView handleException(Exception e) {
            return new ModelAndView(new JspView("error"));
        }
    }

    @ControllerAdvice
    public static class ControllerAdviceSample2 {

        @ExceptionHandler(SampleException.class)
        public ModelAndView handleException(Exception e) {
            return new ModelAndView(new JspView("error"));
        }
    }

    public static class SampleException extends RuntimeException {

    }
}


