package com.interface21.web.exception;

import com.interface21.context.ApplicationContext;
import com.interface21.context.support.AnnotationConfigWebApplicationContext;
import com.interface21.web.code.ExceptionConfiguration;
import com.interface21.webmvc.servlet.ModelAndView;
import com.interface21.webmvc.servlet.mvc.WebMvcConfigurationSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import static org.assertj.core.api.Assertions.assertThat;

class ExceptionHandlerExceptionResolverTest {

    private final ApplicationContext ac = new AnnotationConfigWebApplicationContext(new ControllerAdviceBeanPostProcessor(), ExceptionConfiguration.class, WebMvcConfigurationSupport.class);


    @DisplayName("@ControllerAdvice 의 ExceptionHandler 메서드를 등록 한다")
    @Test
    public void registerExceptionHandlerResolver() throws Exception {
        // when
        final HandlerExceptionResolver resolver = ac.getBean(ExceptionHandlerExceptionResolver.class);
        final ModelAndView actual = resolver.resolveException(new MockHttpServletRequest(), new MockHttpServletResponse(), new Object(), new Exception());

        // then
        assertThat(actual).isNotNull();
    }

}
