package com.interface21.web.bind.annotation;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ExceptionHandler {
    Class<? extends Throwable>[] value() default {};
}
