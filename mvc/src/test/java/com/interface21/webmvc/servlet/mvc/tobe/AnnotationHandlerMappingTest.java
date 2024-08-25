package com.interface21.webmvc.servlet.mvc.tobe;

import com.interface21.context.ApplicationContext;
import com.interface21.context.stereotype.ControllerAdvice;
import com.interface21.web.bind.annotation.ExceptionHandler;
import java.util.Map;
import java.util.Set;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@DisplayName("AnnotationHandlerMapping 클래스의")
class AnnotationHandlerMappingTest {

    private AnnotationHandlerMapping annotationHandlerMapping;
    private ApplicationContext applicationContext;
    private HandlerConverter handlerConverter;
    private ControllerAdviceConverter controllerAdviceConverter;

    @BeforeEach
    public void setUp() {
        applicationContext = mock(ApplicationContext.class);
        handlerConverter = mock(HandlerConverter.class);
        controllerAdviceConverter = new ControllerAdviceConverter();

        annotationHandlerMapping = new AnnotationHandlerMapping(applicationContext, handlerConverter, controllerAdviceConverter);
    }

    @DisplayName("getExceptionHandlers 메서드는")
    @Nested
    class Describe_getExceptionHandlers {
        @DisplayName("ControllerAdvice 어노테이션이 붙은 클래스를 찾아 Map으로 반환한다.")
        @Test
        void it_return_map() {
            // given
            when(applicationContext.getBeanClasses()).thenReturn(Set.of(ExceptionAdvice.class));
            when(applicationContext.getBean(ExceptionAdvice.class)).thenReturn(new ExceptionAdvice());
            // when
            Map<Class<? extends Throwable>, HandlerExecution> exceptionHandlers = annotationHandlerMapping.getExceptionHandlers();
            // then
            assertThat(exceptionHandlers).isNotEmpty();
            assertThat(exceptionHandlers).containsKey(Exception.class);
        }
    }

    @ControllerAdvice
    public static class ExceptionAdvice {

        @ExceptionHandler(Exception.class)
        public void handleException() {
        }
    }
}
