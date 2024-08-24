package com.interface21.webmvc.servlet.mvc.tobe.exception;

import com.interface21.context.ApplicationContext;
import com.interface21.context.annotation.Bean;
import com.interface21.context.annotation.Configuration;
import com.interface21.context.support.AnnotationConfigWebApplicationContext;
import com.interface21.webmvc.servlet.ModelAndView;
import com.interface21.webmvc.servlet.view.JspView;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ExceptionHandlerMappingTest {

    private final ApplicationContext applicationContext = new AnnotationConfigWebApplicationContext(TestConfiguration.class);
    private final ExceptionHandlerConverter exceptionHandlerConverter = new ExceptionHandlerConverter();

    @Test
    void ControllerAdvice가_붙은_클래스의_예외처리_execution을_초기화한다() {
        ExceptionHandlerMapping exceptionHandlerMapping = new ExceptionHandlerMapping(applicationContext, exceptionHandlerConverter);
        exceptionHandlerMapping.initialize();
        assertThat(exceptionHandlerMapping.getHandlerExecutions()).containsOnlyKeys(
                new ExceptionHandlerKey(RuntimeException.class),
                new ExceptionHandlerKey(IllegalArgumentException.class)
        );
    }

    @ControllerAdvice
    public static class TestControllerAdvice {

        @ExceptionHandler(RuntimeException.class)
        public ModelAndView handleRuntimeException() {
            return new ModelAndView(new JspView("redirect:/index.jsp"));
        }

        @ExceptionHandler(IllegalArgumentException.class)
        public ModelAndView handleIllegalArgumentException() {
            return new ModelAndView(new JspView("redirect:/index.jsp"));
        }
    }

    @Configuration
    public static class TestConfiguration {
        @Bean
        public TestControllerAdvice testControllerAdvice() {
            return new TestControllerAdvice();
        }
    }
}
