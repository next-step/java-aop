package samples;

import com.interface21.context.annotation.Bean;
import com.interface21.context.annotation.Configuration;

@Configuration
public class HelloFactoryBeanConfiguration {
    @Bean
    public HelloFactoryBeanWithConfiguration helloFactoryBean() {
        return new HelloFactoryBeanWithConfiguration();
    }
}
