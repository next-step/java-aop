package com.interface21.webmvc.servlet.mvc.tobe.exception;

import com.interface21.context.annotation.Bean;
import com.interface21.context.annotation.Configuration;
import com.interface21.context.stereotype.ControllerAdvice;
import com.interface21.context.support.AnnotationConfigWebApplicationContext;
import com.interface21.webmvc.servlet.ModelAndView;
import com.interface21.webmvc.servlet.mvc.tobe.HandlerExecution;
import com.interface21.webmvc.servlet.view.JspView;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ExceptionHandlerMappingRegistryTest {

    private final ExceptionHandlerMapping exceptionHandlerMapping = new ExceptionHandlerMapping(
            new AnnotationConfigWebApplicationContext(TestConfiguration.class),
            new ExceptionHandlerConverter()
    );

    @Test
    void handerMapping을_초기화한_후_추가한다() {
        ExceptionHandlerMappingRegistry exceptionHandlerMappingRegistry = new ExceptionHandlerMappingRegistry();

        exceptionHandlerMappingRegistry.addExceptionHandlerMapping(exceptionHandlerMapping);
        assertThat(exceptionHandlerMapping.getHandlerExecutions()).containsOnlyKeys(
                new ExceptionHandlerKey(IllegalStateException.class)
        );
    }

    @Test
    void 처리가능한_핸들러가_없는_경우_파라메터인_예외를_던진다() {
        ExceptionHandlerMappingRegistry exceptionHandlerMappingRegistry = new ExceptionHandlerMappingRegistry();
        IllegalAccessException givenException = new IllegalAccessException();

        assertThatThrownBy(() -> exceptionHandlerMappingRegistry.getHandler(givenException))
                .isInstanceOf(IllegalAccessException.class);
    }

    @Test
    void 처리가능한_핸들러가_있는_경우_핸들러를_반환한다() throws Throwable {
        ExceptionHandlerMappingRegistry exceptionHandlerMappingRegistry = new ExceptionHandlerMappingRegistry();
        exceptionHandlerMappingRegistry.addExceptionHandlerMapping(exceptionHandlerMapping);

        HandlerExecution actual = (HandlerExecution) exceptionHandlerMappingRegistry.getHandler(new IllegalStateException());
        assertThat(actual.getMethod()).isEqualTo(TestControllerAdvice.class.getMethod("handleIllegalStateException"));
    }

    @ControllerAdvice
    public static class TestControllerAdvice {

        @ExceptionHandler(IllegalStateException.class)
        public ModelAndView handleIllegalStateException() {
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
