package com.interface21.web.exception;

import com.interface21.webmvc.servlet.ModelAndView;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface HandlerExceptionResolver {
    ModelAndView resolveException(final HttpServletRequest req, final HttpServletResponse res, final Object handler, final Throwable t);
}
