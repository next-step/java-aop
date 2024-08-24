package com.interface21.webmvc.servlet.mvc.tobe.support;

import com.interface21.core.MethodParameter;
import org.junit.jupiter.api.Test;

import java.lang.annotation.Annotation;

import static org.assertj.core.api.Assertions.assertThat;

class ExceptionArgumentResolverTest {

    private final ExceptionArgumentResolver exceptionArgumentResolver = new ExceptionArgumentResolver();

    @Test
    void 파라메터가_Exception하위클래스인_경우_지원한다() throws NoSuchMethodException {
        MethodParameter methodParameter = new MethodParameter(
                Hello.class.getMethod("getAge", RuntimeException.class),
                RuntimeException.class,
                new Annotation[0],
                "getAge"
        );
        boolean actual = exceptionArgumentResolver.supportsParameter(methodParameter);
        assertThat(actual).isTrue();
    }

    @Test
    void 파라메터가_Exception과_연관없는_경우_지원하지_않는다() throws NoSuchMethodException {
        MethodParameter methodParameter = new MethodParameter(
                Hello.class.getMethod("getName", String.class),
                String.class,
                new Annotation[0],
                "getName"
        );
        boolean actual = exceptionArgumentResolver.supportsParameter(methodParameter);
        assertThat(actual).isFalse();
    }

    private static class Hello {
        public String getName(String name) {
            return "jin young";
        }

        public Integer getAge(RuntimeException exception) {
            return 0;
        }
    }
}
