package samples;

import com.interface21.beans.factory.proxy.Advisor;
import com.interface21.beans.factory.ProxyFactoryBean;
import com.interface21.beans.factory.proxy.TypeTarget;
import com.interface21.context.annotation.Bean;
import com.interface21.context.annotation.Configuration;
import com.interface21.transaction.annotation.Transactional;

@Configuration
public class ProxyConfiguration {

    @Bean
    public ProxyFactoryBean test1Service() {
        final ProxyFactoryBean proxyFactoryBean = new ProxyFactoryBean(new FakeBeanFactory());
        final TypeTarget<TestService> typeTarget = new TypeTarget<>(TestService.class);
        proxyFactoryBean.setTargetClass(typeTarget);
        final Advisor advisor = new Advisor(new CounterAdvice());
        proxyFactoryBean.addAdvisors(advisor);

        return proxyFactoryBean;
    }

    @Bean
    public TxTypeBean txTypeBean() {
        return new TxTypeBean();
    }

    @Transactional
    public static class TxTypeBean {

    }

}
