package samples;

import com.interface21.context.annotation.Bean;
import com.interface21.context.annotation.Configuration;
import org.h2.jdbcx.JdbcDataSource;

import javax.sql.DataSource;

@Configuration
public class DatasourceConfiguration {

    @Bean
    public DataSource dataSource() {
        final var jdbcDataSource = new JdbcDataSource();
        jdbcDataSource.setUrl("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;");
        jdbcDataSource.setUser("");
        jdbcDataSource.setPassword("");
        return jdbcDataSource;
    }

    @Bean
    public TransactionalOnClass transactionalOnClass() {
        return new TransactionalOnClass();
    }

    @Bean
    public TransactionalOnMethod transactionalOnMethod() {
        return new TransactionalOnMethod();
    }

    @Bean
    public NoneTransactional noneTransactional() {
        return new NoneTransactional();
    }
}
