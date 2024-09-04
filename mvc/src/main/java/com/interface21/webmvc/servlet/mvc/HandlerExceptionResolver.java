package com.interface21.webmvc.servlet.mvc;

import com.interface21.webmvc.servlet.ModelAndView;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Map;

public interface HandlerExceptionResolver {
    ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Throwable throwable) throws Exception;

    boolean supports(Throwable throwable);

    void addAll(Map<Class<? extends Throwable>, ExceptionHandlerExecution> convert);
}
