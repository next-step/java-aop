package com.interface21.webmvc.servlet.mvc.tobe.exception;

import com.interface21.context.stereotype.ControllerAdvice;
import com.interface21.webmvc.servlet.ModelAndView;
import com.interface21.webmvc.servlet.mvc.tobe.HandlerExecution;
import com.interface21.webmvc.servlet.view.JspView;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class ExceptionHandlerConverterTest {

    @Test
    void ExceptionHandler어노테이션이_붙은_메소드로_handler를_세팅한다() {
        ExceptionHandlerConverter converter = new ExceptionHandlerConverter();

        Map<ExceptionHandlerKey, HandlerExecution> actual = converter.convert(
                Map.of(TestControllerAdvice.class, new TestControllerAdvice())
        );
        assertThat(actual).containsOnlyKeys(
                new ExceptionHandlerKey(RuntimeException.class),
                new ExceptionHandlerKey(IllegalArgumentException.class)
        );
    }

    @ControllerAdvice
    private static class TestControllerAdvice {

        @ExceptionHandler(RuntimeException.class)
        public ModelAndView handleRuntimeException() {
            return new ModelAndView(new JspView("redirect:/index.jsp"));
        }

        @ExceptionHandler(IllegalArgumentException.class)
        public ModelAndView handleIllegalArgumentException() {
            return new ModelAndView(new JspView("redirect:/index.jsp"));
        }
    }
}
