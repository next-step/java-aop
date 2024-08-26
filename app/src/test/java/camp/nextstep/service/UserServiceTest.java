package camp.nextstep.service;

import camp.nextstep.config.MyConfiguration;
import camp.nextstep.dao.UserDao;
import camp.nextstep.domain.User;
import camp.nextstep.support.jdbc.init.DatabasePopulatorUtils;
import com.interface21.context.ApplicationContext;
import com.interface21.context.support.AnnotationConfigWebApplicationContext;
import com.interface21.dao.DataAccessException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Disabled
class UserServiceTest {

    private final ApplicationContext ac = new AnnotationConfigWebApplicationContext(MyConfiguration.class);
    private final UserService userService = ac.getBean(UserService.class);

    @BeforeEach
    void setUp() {
        DatabasePopulatorUtils.execute(ac.getBean(DataSource.class));
        final UserDao userDao = ac.getBean(UserDao.class);
        final var user = new User("gugu", "password", "hkkang@woowahan.com");
        userDao.insert(user);
    }

    @Test
    void testChangePassword() {
        final var newPassword = "qqqqq";
        final var createBy = "gugu";
        userService.changePassword(1L, newPassword, createBy);

        final var actual = userService.findById(1L);

        assertThat(actual.getPassword()).isEqualTo(newPassword);
    }

    @Test
    void testTransactionRollback() {
        final ApplicationContext ac = new AnnotationConfigWebApplicationContext(FailConfiguration.class);
        final UserService userService = ac.getBean(UserService.class);

        // 트랜잭션 롤백 테스트를 위해 stub으로 교체
        final var newPassword = "newPassword";
        final var createBy = "gugu";

        // 트랜잭션이 정상 동작하는지 확인하기 위해 의도적으로 StubUserHistoryDao에서 예외를 발생시킨다.
        assertThrows(DataAccessException.class,
            () -> userService.changePassword(1L, newPassword, createBy));

        final var actual = userService.findById(1L);

        assertThat(actual.getPassword()).isNotEqualTo(newPassword);
    }
}
