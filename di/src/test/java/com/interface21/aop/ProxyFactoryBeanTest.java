package com.interface21.aop;

import com.interface21.aop.advisor.Advisor;
import org.junit.jupiter.api.Test;
import samples.HelloAroundAdvice;
import samples.World;
import samples.WorldObject;

import static org.assertj.core.api.Assertions.assertThat;

class ProxyFactoryBeanTest {

    @Test
    void proxyFactoryBeanTest() {
        World target = new WorldObject("new world");
        ProxyFactoryBean<World> proxyFactoryBean = new ProxyFactoryBean<>();
        proxyFactoryBean.setTarget(target);
        proxyFactoryBean.setInterfaces(new Class[]{World.class});
        proxyFactoryBean.setObjectType(World.class);
        proxyFactoryBean.addAdvisor(new Advisor(new HelloAroundAdvice()));

        World proxy = proxyFactoryBean.getObject();
        assertThat(target.getMessage()).isEqualTo("new world");
        assertThat(proxy.getMessage()).isEqualTo("Hello, new world!");
    }
}
