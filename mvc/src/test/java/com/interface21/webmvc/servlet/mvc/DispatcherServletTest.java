package com.interface21.webmvc.servlet.mvc;

import com.interface21.context.annotation.Bean;
import com.interface21.context.annotation.Configuration;
import com.interface21.context.stereotype.Controller;
import com.interface21.context.stereotype.ControllerAdvice;
import com.interface21.context.support.AnnotationConfigWebApplicationContext;
import com.interface21.web.bind.annotation.RequestMapping;
import com.interface21.web.bind.annotation.RequestMethod;
import com.interface21.web.method.support.HandlerMethodArgumentResolver;
import com.interface21.webmvc.servlet.ModelAndView;
import com.interface21.webmvc.servlet.mvc.asis.ControllerHandlerAdapter;
import com.interface21.webmvc.servlet.mvc.asis.ManualHandlerMapping;
import com.interface21.webmvc.servlet.mvc.tobe.AnnotationHandlerMapping;
import com.interface21.webmvc.servlet.mvc.tobe.HandlerConverter;
import com.interface21.webmvc.servlet.mvc.tobe.HandlerExecutionHandlerAdapter;
import com.interface21.webmvc.servlet.mvc.tobe.exception.ExceptionHandler;
import com.interface21.webmvc.servlet.mvc.tobe.exception.ExceptionHandlerConverter;
import com.interface21.webmvc.servlet.mvc.tobe.exception.ExceptionHandlerMapping;
import com.interface21.webmvc.servlet.mvc.tobe.support.*;
import com.interface21.webmvc.servlet.view.JspView;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static java.util.Arrays.asList;
import static org.mockito.Mockito.*;

class DispatcherServletTest {

    private final HttpServletRequest request = mock(HttpServletRequest.class);
    private final HttpServletResponse response = mock(HttpServletResponse.class);
    private final RequestDispatcher requestDispatcher = mock(RequestDispatcher.class);

    private DispatcherServlet dispatcherServlet;

    @BeforeEach
    void setUp() {
        final var applicationContext = new AnnotationConfigWebApplicationContext(TestConfiguration.class);
        final var handlerConverter = applicationContext.getBean(HandlerConverter.class);
        final var annotationHandlerMapping = new AnnotationHandlerMapping(applicationContext, handlerConverter);
        annotationHandlerMapping.initialize();
        final var exceptionHandlerConverter = applicationContext.getBean(ExceptionHandlerConverter.class);
        final var exceptionHandlerMapping = new ExceptionHandlerMapping(applicationContext, exceptionHandlerConverter);
        exceptionHandlerMapping.initialize();

        dispatcherServlet = new DispatcherServlet(exceptionHandlerMapping);
        dispatcherServlet.addHandlerMapping(new ManualHandlerMapping());
        dispatcherServlet.addHandlerMapping(new AnnotationHandlerMapping(applicationContext, handlerConverter));

        dispatcherServlet.addHandlerAdapter(new ControllerHandlerAdapter());
        dispatcherServlet.addHandlerAdapter(new HandlerExecutionHandlerAdapter());
        dispatcherServlet.init();
    }

    @Test
    void 정상_url을_매핑한다() throws IOException, ServletException {
        when(request.getRequestURI()).thenReturn("/api/success");
        when(request.getMethod()).thenReturn("GET");
        when(request.getRequestDispatcher("/index.jsp")).thenReturn(requestDispatcher);
        doNothing().when(requestDispatcher).forward(request, response);

        dispatcherServlet.service(request, response);
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    void 예외에_해당하는_핸들러로_핸들링한다() throws ServletException, IOException {
        when(request.getRequestURI()).thenReturn("/api/fail");
        when(request.getMethod()).thenReturn("GET");
        when(request.getRequestDispatcher("/index.jsp")).thenReturn(requestDispatcher);
        doNothing().when(response).sendRedirect("/index.jsp");
        doNothing().when(requestDispatcher).forward(request, response);

        dispatcherServlet.service(request, response);
        verify(response).sendRedirect("/index.jsp");
    }

    @ControllerAdvice
    public static class TestControllerAdvice {

        @ExceptionHandler(IllegalArgumentException.class)
        public ModelAndView handleIllegalArgumentException() {
            return new ModelAndView(new JspView("redirect:/index.jsp"));
        }
    }

    @Controller
    public static class TestController {
        @RequestMapping(value = "/api/success", method = RequestMethod.GET)
        public ModelAndView success() {
            return new ModelAndView(new JspView("/index.jsp"));
        }

        @RequestMapping(value = "/api/fail", method = RequestMethod.GET)
        public String fail() {
            throw new IllegalArgumentException();
        }
    }

    @Configuration
    public static class TestConfiguration {
        @Bean
        public TestControllerAdvice testControllerAdvice() {
            return new TestControllerAdvice();
        }

        @Bean
        public TestController testController() {
            return new TestController();
        }

        @Bean
        public HandlerConverter handlerConverter() {
            HandlerConverter handlerConverter = new HandlerConverter();
            handlerConverter.setArgumentResolvers(defaultArgumentResolvers());
            return handlerConverter;
        }

        @Bean
        public ExceptionHandlerConverter exceptionHandlerConverter() {
            return new ExceptionHandlerConverter();
        }

        private List<HandlerMethodArgumentResolver> defaultArgumentResolvers() {
            return asList(
                    new HttpRequestArgumentResolver(),
                    new HttpResponseArgumentResolver(),
                    new RequestParamArgumentResolver(),
                    new PathVariableArgumentResolver(),
                    new ModelArgumentResolver()
            );
        }
    }
}
