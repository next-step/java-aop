package samples;

import com.interface21.beans.factory.FactoryBean;
import com.interface21.beans.factory.proxy.Advisor;
import com.interface21.beans.factory.proxy.ProxyFactoryBean;
import com.interface21.beans.factory.proxy.Target;
import com.interface21.context.annotation.Bean;
import com.interface21.context.annotation.Configuration;

@Configuration
public class ProxyFactoryConfiguration {

    @Bean
    public FactoryBean<NameService> userService() {
        Advisor advisor = new Advisor(
                method -> method.getName().equals("getName"),
                new UpperCaseAdvice()
        );
        return new ProxyFactoryBean<>(new Target<>(NameService.class), advisor);
    }
}
