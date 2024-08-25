package com.interface21.beans.factory;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import sample.HelloTarget;

class ProxyFactoryBeanTest {

    @Test
    void testProxyFactoryBean() {
        Advice advice = Mockito.mock(Advice.class);
        PointCut pointCut = Mockito.mock(PointCut.class);
        ProxyFactoryBean proxyFactoryBean = new ProxyFactoryBean(HelloTarget.class, advice, pointCut);

        proxyFactoryBean.setAdvice(advice);
        proxyFactoryBean.setPointCut(pointCut);
        when(pointCut.matches(any())).thenReturn(true);

        HelloTarget helloTarget = (HelloTarget) proxyFactoryBean.getObject();

        helloTarget.sayHello("test");

        verify(pointCut, times(1)).matches(any());
        verify(advice, times(1)).around(any());
    }
}
