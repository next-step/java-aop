package com.interface21.beans.factory.proxy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;

import java.lang.reflect.Method;

import static org.mockito.Mockito.*;

class AfterAdviceTest {

    private final Method method = Simple.class.getMethod("say");
    private final Object[] arguments = new Object[]{};
    private final JoinPoint joinPoint = mock(JoinPoint.class);

    AfterAdviceTest() throws NoSuchMethodException {
    }

    @BeforeEach
    void setUp() throws Throwable {
        when(joinPoint.getMethod()).thenReturn(method);
        when(joinPoint.getArguments()).thenReturn(arguments);
        when(joinPoint.proceed()).thenReturn("say");
    }

    @Test
    void joinPoint를_실행한_후_afterReturning을_실행한다() throws Throwable {
        AfterAdvice afterAdvice = spy(new AfterAdvice() {
            @Override
            void afterReturning(Object returnValue, Method method, Object[] args) {
            }
        });
        afterAdvice.invoke(joinPoint);

        InOrder inOrder = inOrder(joinPoint, afterAdvice);
        inOrder.verify(joinPoint).proceed();
        inOrder.verify(afterAdvice).afterReturning("say", method, arguments);
    }

    private static class Simple {
        public String say() {
            return "say";
        }
    }
}
