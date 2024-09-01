package camp.nextstep.config;

import camp.nextstep.service.UserService;
import com.interface21.context.support.AnnotationConfigWebApplicationContext;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MyConfigurationTest {
    @Test
    void configTest() {
        final var applicationContext = new AnnotationConfigWebApplicationContext(MyConfiguration.class);
        UserService bean = applicationContext.getBean(UserService.class);
        assertThat(bean).isNotNull();
    }
}
