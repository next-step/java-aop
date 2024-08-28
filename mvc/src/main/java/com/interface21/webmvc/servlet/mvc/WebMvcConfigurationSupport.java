package com.interface21.webmvc.servlet.mvc;

import com.interface21.beans.factory.BeanFactory;
import com.interface21.context.annotation.Bean;
import com.interface21.context.annotation.Configuration;
import com.interface21.web.exception.ExceptionHandlerExceptionResolver;

@Configuration
public class WebMvcConfigurationSupport {

    @Bean
    public ExceptionHandlerExceptionResolver exceptionHandlerExceptionResolver(final BeanFactory beanFactory) {
        return new ExceptionHandlerExceptionResolver(beanFactory);
    }

    @Bean
    public HandlerExceptionRegistry handlerExceptionRegistry() {
        return new HandlerExceptionRegistry();
    }
}
