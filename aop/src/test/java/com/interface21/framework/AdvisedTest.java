package com.interface21.framework;

import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@DisplayName("Advised 테스트")
class AdvisedTest {

    private Advised advised;
    private Class<?> targetClass;
    private List<Advisor> advisors;

    @BeforeEach
    void setUp() {
        targetClass = HelloService.class;
        advisors = new ArrayList<>();
        advised = new Advised(targetClass, advisors);
    }

    @DisplayName("before advice를 실행한다.")
    @Test
    void testBeforeAdvice() throws Throwable {
        //given
        MethodInvocation invocation = mock(MethodInvocation.class);
        Advisor beforeAdvisor = mock(Advisor.class);
        when(beforeAdvisor.isBeforeAdvice()).thenReturn(true);
        advised.addAdvisor(beforeAdvisor);

        //when
        advised.before(invocation);

        //then
        verify(beforeAdvisor, times(1)).invoke(invocation);
    }

    @DisplayName("after advice를 실행한다.")
    @Test
    void testAfterAdvice() throws Throwable {
        //given
        MethodInvocation invocation = mock(MethodInvocation.class);
        Advisor afterAdvisor = mock(Advisor.class);
        when(afterAdvisor.isAfterAdvice()).thenReturn(true);
        advised.addAdvisor(afterAdvisor);

        //when
        advised.after(invocation);

        //then
        verify(afterAdvisor, times(1)).invoke(invocation);
    }

    @DisplayName("after returning advice를 실행한다.")
    @Test
    void testAfterReturningAdvice() throws Throwable {
        //given
        MethodInvocation invocation = mock(MethodInvocation.class);
        Advisor afterReturningAdvisor = mock(Advisor.class);
        when(afterReturningAdvisor.isAfterReturningAdvice()).thenReturn(true);
        advised.addAdvisor(afterReturningAdvisor);

        //when
        advised.afterReturning(invocation);

        //then
        verify(afterReturningAdvisor, times(1)).invoke(invocation);
    }

    @DisplayName("advice 실행도중 예외가 발생하면 예외를 던진다.")
    @Test
    void testInvokeAdviceWithException() throws Throwable {
        //given
        MethodInvocation invocation = mock(MethodInvocation.class);
        Advisor failingAdvisor = mock(Advisor.class);
        when(failingAdvisor.isBeforeAdvice()).thenReturn(true);
        doThrow(new RuntimeException("Test Exception")).when(failingAdvisor).invoke(invocation);
        advised.addAdvisor(failingAdvisor);

        //when && then
        assertThrowsExactly(RuntimeException.class, () -> advised.before(invocation));
    }
}
