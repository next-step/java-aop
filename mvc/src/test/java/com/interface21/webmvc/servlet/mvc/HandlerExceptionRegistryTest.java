package com.interface21.webmvc.servlet.mvc;

import com.interface21.web.code.FakeApplicationContext;
import com.interface21.webmvc.servlet.ModelAndView;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import static org.assertj.core.api.Assertions.assertThat;

class HandlerExceptionRegistryTest {

    @DisplayName("처리할 수 있는 예외가 발생 하면 처리 후 ModelAndView 를 반환 한다")
    @Test
    public void resolveException() throws Exception {
        // given
        final HandlerExceptionRegistry handlerExceptionRegistry = new HandlerExceptionRegistry();
        handlerExceptionRegistry.initApplicationContext(new FakeApplicationContext());

        // when
        final ModelAndView actual = handlerExceptionRegistry.resolveException(new MockHttpServletRequest(), new MockHttpServletResponse(), new Object(), new Exception());

        // then
        assertThat(actual).isNotNull();
    }
    
    @DisplayName("처리할 수 없는 예외가 발생 하면 null 을 반환 한다")
    @Test
    public void notResolveException() throws Exception {
        // given
        final HandlerExceptionRegistry handlerExceptionRegistry = new HandlerExceptionRegistry();
        handlerExceptionRegistry.initApplicationContext(new FakeApplicationContext());

        // when
        final ModelAndView actual = handlerExceptionRegistry.resolveException(new MockHttpServletRequest(), new MockHttpServletResponse(), new Object(), new Throwable());

        // then
        assertThat(actual).isNull();
    }

}
