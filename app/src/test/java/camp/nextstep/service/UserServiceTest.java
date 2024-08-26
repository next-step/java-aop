package camp.nextstep.service;

import camp.nextstep.config.MyConfiguration;
import camp.nextstep.dao.UserDao;
import camp.nextstep.domain.User;
import camp.nextstep.support.jdbc.init.DatabasePopulatorUtils;
import com.interface21.context.ApplicationContext;
import com.interface21.context.support.AnnotationConfigWebApplicationContext;
import com.interface21.dao.DataAccessException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UserServiceTest {

    private UserService userService;

    @BeforeEach
    void setUp() {
        final ApplicationContext applicationContext = new AnnotationConfigWebApplicationContext(MyConfiguration.class);
        this.userService = applicationContext.getBean(UserService.class);

        final DataSource dataSource = applicationContext.getBean(DataSource.class);
        DatabasePopulatorUtils.execute(dataSource);

        final var user = new User("gugu", "password", "hkkang@woowahan.com");
        final UserDao userDao = applicationContext.getBean(UserDao.class);
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
        final String newPassword = null;
        final var createBy = "gugu";

        assertThrows(DataAccessException.class,
                () -> userService.changePassword(1L, newPassword, createBy));

        final var actual = userService.findById(1L);

        assertThat(actual.getPassword()).isNotEqualTo(newPassword);
    }
}
