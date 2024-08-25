package camp.nextstep.config;

import com.interface21.context.annotation.Bean;
import com.interface21.context.annotation.ComponentScan;
import com.interface21.context.annotation.Configuration;
import com.interface21.jdbc.core.JdbcTemplate;
import com.interface21.transaction.PlatformTransactionManager;
import com.interface21.transaction.support.DataSourceTransactionManager;
import com.interface21.web.method.support.HandlerMethodArgumentResolver;
import com.interface21.webmvc.servlet.mvc.tobe.ControllerAdviceConverter;
import com.interface21.webmvc.servlet.mvc.tobe.HandlerConverter;
import com.interface21.webmvc.servlet.mvc.tobe.support.*;
import org.h2.jdbcx.JdbcDataSource;

import javax.sql.DataSource;
import java.util.List;

import static java.util.Arrays.asList;

@Configuration
@ComponentScan({ "camp.nextstep", "com.interface21" })
public class MyConfiguration {

    @Bean
    public DataSource dataSource() {
        final var jdbcDataSource = new JdbcDataSource();
        jdbcDataSource.setUrl("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;");
        jdbcDataSource.setUser("");
        jdbcDataSource.setPassword("");
        return jdbcDataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate(final DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public PlatformTransactionManager platformTransactionManager(final DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean
    public HandlerConverter handlerConverter() {
        HandlerConverter handlerConverter = new HandlerConverter();
        handlerConverter.setArgumentResolvers(defaultArgumentResolvers());
        return handlerConverter;
    }

    @Bean
    public ControllerAdviceConverter controllerAdviceConverter() {
        ControllerAdviceConverter controllerAdviceConverter = new ControllerAdviceConverter();
        controllerAdviceConverter.setArgumentResolvers(defaultExceptionArgumentResolvers());
        return controllerAdviceConverter;
    }

    List<HandlerMethodArgumentResolver> defaultArgumentResolvers() {
        return asList(
            new HttpRequestArgumentResolver(),
            new HttpResponseArgumentResolver(),
            new RequestParamArgumentResolver(),
            new PathVariableArgumentResolver(),
            new ModelArgumentResolver()
        );
    }

    List<HandlerMethodArgumentResolver> defaultExceptionArgumentResolvers() {
        return asList(
            new HttpRequestArgumentResolver(),
            new HttpResponseArgumentResolver()
        );
    }
}
