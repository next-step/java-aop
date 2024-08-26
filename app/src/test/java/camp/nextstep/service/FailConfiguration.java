package camp.nextstep.service;

import camp.nextstep.dao.UserDao;
import camp.nextstep.dao.UserHistoryDao;
import com.interface21.context.annotation.Bean;
import com.interface21.context.annotation.Configuration;
import com.interface21.jdbc.core.JdbcTemplate;
import com.interface21.transaction.PlatformTransactionManager;
import com.interface21.transaction.support.DataSourceTransactionManager;
import org.h2.jdbcx.JdbcDataSource;

import javax.sql.DataSource;

@Configuration
public class FailConfiguration {

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
    public PlatformTransactionManager transactionManager(final DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean
    public UserDao userDao(final JdbcTemplate jdbcTemplate) {
        return new UserDao(jdbcTemplate);
    }

    @Bean
    public UserHistoryDao userHistoryDao(final JdbcTemplate jdbcTemplate) {
        return new StubUserHistoryDao(jdbcTemplate);
    }

    @Bean
    public UserService userService(final UserDao userDao, final UserHistoryDao userHistoryDao) {
        return new UserService(userDao, userHistoryDao);
    }

}
