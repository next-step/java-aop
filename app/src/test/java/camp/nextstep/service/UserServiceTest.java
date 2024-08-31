package camp.nextstep.service;

import camp.nextstep.config.MyConfiguration;
import camp.nextstep.dao.UserDao;
import camp.nextstep.dao.UserHistoryDao;
import camp.nextstep.domain.User;
import camp.nextstep.support.jdbc.init.DatabasePopulatorUtils;
import com.interface21.dao.DataAccessException;
import com.interface21.jdbc.core.JdbcTemplate;
import com.interface21.transaction.support.DataSourceTransactionManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Disabled
class UserServiceTest {

    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;
    private UserDao userDao;

    @BeforeEach
    void setUp() {
        final var myConfiguration = new MyConfiguration();
        this.dataSource = myConfiguration.dataSource();
        DatabasePopulatorUtils.execute(dataSource);

        this.jdbcTemplate = myConfiguration.jdbcTemplate(dataSource);
        this.userDao = new UserDao(jdbcTemplate);
        final var user = new User("gugu", "password", "hkkang@woowahan.com");
        userDao.insert(user);
    }

    @Test
    void testChangePassword() {
        final var userHistoryDao = new UserHistoryDao(jdbcTemplate);
        final var userService = new UserService(userDao, userHistoryDao);

        final var newPassword = "qqqqq";
        final var createBy = "gugu";
        userService.changePassword(1L, newPassword, createBy);

        final var actual = userService.findById(1L);

        assertThat(actual.getPassword()).isEqualTo(newPassword);
    }

    @Test
    void testTransactionRollback() {
        // 트랜잭션 롤백 테스트를 위해 stub으로 교체
        final var userHistoryDao = new StubUserHistoryDao(jdbcTemplate);
        // 애플리케이션 서비스
        final var appUserService = new UserService(userDao, userHistoryDao);
        // 트랜잭션 서비스 추상화
        final var transactionManager = new DataSourceTransactionManager(dataSource);
        final var userService = new TxUserService(transactionManager, appUserService);

        final var newPassword = "newPassword";
        final var createBy = "gugu";

        // 트랜잭션이 정상 동작하는지 확인하기 위해 의도적으로 StubUserHistoryDao에서 예외를 발생시킨다.
        assertThrows(DataAccessException.class,
            () -> userService.changePassword(1L, newPassword, createBy));

        final var actual = userService.findById(1L);

        assertThat(actual.getPassword()).isNotEqualTo(newPassword);
    }
}
