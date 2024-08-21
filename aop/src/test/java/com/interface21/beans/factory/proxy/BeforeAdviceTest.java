package com.interface21.beans.factory.proxy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;

import java.lang.reflect.Method;

import static org.mockito.Mockito.*;

class BeforeAdviceTest {

    private final Method method = Simple.class.getMethod("say");
    private final Object[] arguments = new Object[]{};
    private final JoinPoint joinPoint = mock(JoinPoint.class);

    BeforeAdviceTest() throws NoSuchMethodException {
    }

    @BeforeEach
    void setUp() throws Throwable {
        when(joinPoint.getMethod()).thenReturn(method);
        when(joinPoint.getArguments()).thenReturn(arguments);
        when(joinPoint.proceed()).thenReturn("say");
    }

    @Test
    void joinPoint를_실행하기_전_before를_먼저_실행한다() throws Throwable {
        BeforeAdvice beforeAdvice = spy(new BeforeAdvice() {
            @Override
            void before(Method method, Object[] args) {
            }
        });
        beforeAdvice.invoke(joinPoint);

        InOrder inOrder = inOrder(joinPoint, beforeAdvice);
        inOrder.verify(beforeAdvice).before(method, arguments);
        inOrder.verify(joinPoint).proceed();
    }

    private static class Simple {
        public String say() {
            return "say";
        }
    }
}
