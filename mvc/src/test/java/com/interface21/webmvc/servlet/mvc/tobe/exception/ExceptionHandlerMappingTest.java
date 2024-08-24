package com.interface21.webmvc.servlet.mvc.tobe.exception;

import com.interface21.context.ApplicationContext;
import com.interface21.context.annotation.Bean;
import com.interface21.context.annotation.Configuration;
import com.interface21.context.support.AnnotationConfigWebApplicationContext;
import com.interface21.webmvc.servlet.ModelAndView;
import com.interface21.webmvc.servlet.view.JspView;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ExceptionHandlerMappingTest {

    private final ApplicationContext applicationContext = new AnnotationConfigWebApplicationContext(TestConfiguration.class);
    private final ExceptionHandlerConverter exceptionHandlerConverter = new ExceptionHandlerConverter();

    @Test
    void ControllerAdvice가_붙은_클래스의_예외처리_execution을_초기화한다() {
        ExceptionHandlerMapping exceptionHandlerMapping = new ExceptionHandlerMapping(applicationContext, exceptionHandlerConverter);
        exceptionHandlerMapping.initialize();
        assertThat(exceptionHandlerMapping.getHandlerExecutions()).containsOnlyKeys(
                new ExceptionHandlerKey(IllegalStateException.class),
                new ExceptionHandlerKey(IllegalArgumentException.class)
        );
    }

    @Test
    void 예외에_맞는_핸들러를_못가져오는_경우_예외를_그대로_던진다() {
        ExceptionHandlerMapping exceptionHandlerMapping = new ExceptionHandlerMapping(applicationContext, exceptionHandlerConverter);
        exceptionHandlerMapping.initialize();
        assertThatThrownBy(() -> exceptionHandlerMapping.getHandler(new IllegalAccessException()))
                .isInstanceOf(IllegalAccessException.class);
    }

    @Test
    void 예외에_해당하는_핸들러를_반환한다() throws Exception {
        ExceptionHandlerMapping exceptionHandlerMapping = new ExceptionHandlerMapping(applicationContext, exceptionHandlerConverter);
        exceptionHandlerMapping.initialize();

        ExceptionHandlerExecution actual = exceptionHandlerMapping.getHandler(new IllegalArgumentException());
        assertThat(actual.getMethod()).isEqualTo(TestControllerAdvice.class.getMethod("handleIllegalArgumentException"));
    }

    @ControllerAdvice
    public static class TestControllerAdvice {

        @ExceptionHandler(IllegalStateException.class)
        public ModelAndView handleIllegalStateException() {
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
