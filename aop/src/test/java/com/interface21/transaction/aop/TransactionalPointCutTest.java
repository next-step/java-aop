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

    @Transactional
    private static class ClassTransactional {

        public String getName() {
            return "jinyoung";
        }
    }
}
