package samples;

import com.interface21.beans.factory.proxy.Advisor;
import com.interface21.beans.factory.proxy.ProxyFactoryBean;
import com.interface21.beans.factory.proxy.TypeTarget;
import com.interface21.context.annotation.Bean;
import com.interface21.context.annotation.Configuration;

@Configuration
public class ProxyConfiguration {

    @Bean
    public ProxyFactoryBean test1Service() {
        final ProxyFactoryBean proxyFactoryBean = new ProxyFactoryBean();
        final TypeTarget<TestService> typeTarget = new TypeTarget<>(TestService.class);
        proxyFactoryBean.setTargetClass(typeTarget);
        final Advisor advisor = new Advisor(new CounterAdvice());
        proxyFactoryBean.addAdvisors(advisor);

        return proxyFactoryBean;
    }

}
