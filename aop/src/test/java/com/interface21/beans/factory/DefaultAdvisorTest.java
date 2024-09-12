package com.interface21.beans.factory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.interface21.beans.factory.proxy.DefaultAdvisor;
import com.interface21.beans.factory.proxy.advice.Interceptor;
import com.interface21.beans.factory.proxy.joinpoint.MethodInvocation;
import com.interface21.beans.factory.proxy.pointcut.Pointcut;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class DefaultAdvisorTest {
    private Pointcut pointcut;
    private Interceptor advice;
    private DefaultAdvisor advisor;
    private MethodInvocation methodInvocation;

    @BeforeEach
    public void setUp() {
        pointcut = mock(Pointcut.class);
        advice = mock(Interceptor.class);
        advisor = new DefaultAdvisor(pointcut, advice);
        methodInvocation = mock(MethodInvocation.class);
    }

    @Test
    @DisplayName("invoke가 정상적으로 호출됩니다.")
    public void testInvoke() throws Throwable {
        when(advice.invoke(methodInvocation)).thenReturn("result");
        Object result = advisor.invoke(methodInvocation);
        assertEquals("result", result);
        verify(advice).invoke(methodInvocation);
    }
}
