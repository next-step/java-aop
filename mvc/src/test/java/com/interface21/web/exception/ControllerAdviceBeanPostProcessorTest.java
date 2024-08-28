package com.interface21.web.exception;

import com.interface21.context.ApplicationContext;
import com.interface21.context.support.AnnotationConfigWebApplicationContext;
import com.interface21.web.code.ExControllerAdvice;
import com.interface21.web.code.ExceptionConfiguration;
import com.interface21.webmvc.servlet.mvc.WebMvcConfigurationSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ControllerAdviceBeanPostProcessorTest {

    private final ApplicationContext ac = new AnnotationConfigWebApplicationContext(new ControllerAdviceBeanPostProcessor(), ExceptionConfiguration.class, WebMvcConfigurationSupport.class);

    @DisplayName("@ControllerAdvice 를 bean 등록 한다")
    @Test
    public void registerControllerAdvice() throws Exception {
        // when
        final ExControllerAdvice actual = ac.getBean(ExControllerAdvice.class);

        // then
        assertThat(actual).isNotNull().isInstanceOf(ExControllerAdvice.class);
    }
}
