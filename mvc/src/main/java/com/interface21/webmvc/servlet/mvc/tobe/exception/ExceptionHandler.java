package com.interface21.webmvc.servlet.mvc.tobe.exception;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ExceptionHandler {

    Class<? extends Exception>[] value() default {};
}
