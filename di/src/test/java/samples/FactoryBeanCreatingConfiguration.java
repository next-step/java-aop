package samples;

import com.interface21.aop.ProxyFactoryBean;
import com.interface21.aop.advisor.Advisor;
import com.interface21.context.annotation.Bean;
import com.interface21.context.annotation.ComponentScan;
import com.interface21.context.annotation.Configuration;

@Configuration
@ComponentScan("samples")
public class FactoryBeanCreatingConfiguration {

    @SuppressWarnings("unused")
    @Bean
    public ProxyFactoryBean<World> worldProxy() {
        ProxyFactoryBean<World> proxyFactoryBean = new ProxyFactoryBean<>();
        proxyFactoryBean.setTargetClass(WorldObject.class);
        proxyFactoryBean.setObjectType(World.class);
        proxyFactoryBean.addAdvisor(new Advisor(new HelloAroundAdvice()));

        return proxyFactoryBean;
    }
}
