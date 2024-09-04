package samples;

import com.interface21.beans.factory.ProxyFactoryBean;
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
        return new ProxyFactoryBean<>(
                WorldObject.class,
                World.class,
                new Advisor(new HelloAroundAdvice())
        );
    }
}
