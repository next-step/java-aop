package com.interface21.framework;

import java.lang.reflect.Method;
import java.util.List;
import net.sf.cglib.proxy.MethodProxy;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@DisplayName("CglibMethodInterceptor 테스트")
class CglibMethodInterceptorTest {

    private Method method;
    private MethodProxy proxy;

    @BeforeEach
    public void setUp() throws NoSuchMethodException {
        method = HelloService.class.getMethod("sayHello", String.class);
        proxy = mock(MethodProxy.class);
    }

    @DisplayName("advisors가 없는 경우의 intercept 메서드를 호출한다.")
    @Test
    void testInterceptWithNoAdvisors() throws Throwable {
        // given
        String returnValue = "Hello, World";
        Advised advised = new Advised(HelloService.class);
        CglibMethodInterceptor interceptor = new CglibMethodInterceptor(advised);
        when(proxy.invokeSuper(any(), any())).thenReturn(returnValue);

        // when
        Object result = interceptor.intercept(new HelloService(), method, new String[]{}, proxy);

        // then
        verify(proxy, times(1)).invokeSuper(any(), any());
        assertThat(result).isEqualTo(returnValue);
    }

    @DisplayName("advisors가 있는 경우의 intercept 메서드를 호출한다.")
    @Test
    void testInterceptWithAdvisors() throws Throwable {
        // given
        String returnValue = "Hello, World";
        Advisor afterReturningAdvisor = mock(Advisor.class);
        when(afterReturningAdvisor.isAfterReturningAdvice()).thenReturn(true);
        setupAfterReturningAdvisor(afterReturningAdvisor);

        Advised advised = new Advised(HelloService.class, List.of(afterReturningAdvisor));
        CglibMethodInterceptor interceptor = new CglibMethodInterceptor(advised);

        when(proxy.invokeSuper(any(), any())).thenReturn(returnValue);

        // when
        Object result = interceptor.intercept(new HelloService(), method, new String[]{}, proxy);

        // then
        verify(afterReturningAdvisor, times(1)).invoke(any());
        assertThat(result).isEqualTo(returnValue.toUpperCase());
    }

    @DisplayName("예외가 발생하는 경우의 intercept 메서드를 호출한다.")
    @Test
    void testInterceptWithException() throws Throwable {
        // given
        Advisor afterReturningAdvisor = mock(Advisor.class);
        when(afterReturningAdvisor.isAfterReturningAdvice()).thenReturn(true);
        setupAfterReturningAdvisor(afterReturningAdvisor);

        Advised advised = new Advised(HelloService.class, List.of(afterReturningAdvisor));
        CglibMethodInterceptor interceptor = new CglibMethodInterceptor(advised);

        when(proxy.invokeSuper(any(), any())).thenThrow(new RuntimeException("예외 발생"));

        // when
        assertThatThrownBy(() -> interceptor.intercept(new HelloService(), method, new String[]{}, proxy))
            .isInstanceOf(RuntimeException.class);
    }

    private void setupAfterReturningAdvisor(Advisor afterReturningAdvisor) throws Throwable {
        doAnswer(invocation -> {
            MethodInvocation methodInvocation = invocation.getArgument(0);
            methodInvocation.setReturnValue(methodInvocation.getReturnValue().toString().toUpperCase());
            return null;
        }).when(afterReturningAdvisor).invoke(any());
    }
}
