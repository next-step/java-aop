package com.interface21.webmvc.servlet.mvc.tobe.support;

import com.interface21.core.MethodParameter;
import com.interface21.web.method.support.HandlerMethodArgumentResolver;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ExceptionArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return Throwable.class.isAssignableFrom(parameter.getType());
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, HttpServletRequest request, HttpServletResponse response) {
        return request.getAttribute("exception");
    }
}
