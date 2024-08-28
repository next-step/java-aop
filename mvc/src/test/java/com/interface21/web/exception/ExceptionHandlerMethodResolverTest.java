package com.interface21.web.exception;

import com.interface21.web.code.ExControllerAdvice;
import com.interface21.web.code.MvcTestBeanFactory;
import com.interface21.webmvc.servlet.ModelAndView;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.assertThat;

class ExceptionHandlerMethodResolverTest {

    @DisplayName("ControllerAdvice 로 생성한 ExceptionResolver 를 실행 한다")
    @Test
    public void invokeExceptionResolverMethod() throws Exception {
        // given
        final MvcTestBeanFactory beanFactory = new MvcTestBeanFactory();
        final ExControllerAdvice advice = new ExControllerAdvice();
        final Method exception = ExControllerAdvice.class.getMethod("exception", Exception.class);
        final ExceptionHandlerMethodResolver methodResolver = new ExceptionHandlerMethodResolver(beanFactory, advice, exception);

        // when
        final ModelAndView actual = methodResolver.resolveException(new Exception());

        // then
        assertThat(actual).isNotNull();
    }

}
