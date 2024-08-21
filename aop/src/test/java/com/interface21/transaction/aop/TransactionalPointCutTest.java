package com.interface21.transaction.aop;

import com.interface21.transaction.annotation.Transactional;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.assertThat;

class TransactionalPointCutTest {

    @Test
    void 클래스에_Transactional_어노테이션이_있는_경우_true를_반환한다() throws NoSuchMethodException {
        Method method = ClassTransactional.class.getMethod("getName");

        boolean actual = new TransactionalPointCut().matches(method);
        assertThat(actual).isTrue();
    }

    @Test
    void 메소드에_Transactional_어노테이션이_있는_경우_true를_반환한다() throws NoSuchMethodException {
        Method method = MethodTransactional.class.getMethod("yesTransactional");

        boolean actual = new TransactionalPointCut().matches(method);
        assertThat(actual).isTrue();
    }

    @Test
    void Transcational이_없는_경우_false를_반환한다() throws NoSuchMethodException {
        Method method = MethodTransactional.class.getMethod("noTransactional");

        boolean actual = new TransactionalPointCut().matches(method);
        assertThat(actual).isFalse();
    }

    @Transactional
    private static class ClassTransactional {

        public String getName() {
            return "jinyoung";
        }
    }

    private static class MethodTransactional {

        @Transactional
        public String yesTransactional() {
            return "yes";
        }

        public String noTransactional() {
            return "no";
        }
    }
}
