package com.interface21.webmvc.servlet.mvc.tobe.exception;

import com.interface21.context.stereotype.Component;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface ControllerAdvice {
}
