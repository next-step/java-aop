package camp.nextstep.service;

import camp.nextstep.config.MyConfiguration;
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

    private final ApplicationContext applicationContext = new AnnotationConfigWebApplicationContext(MyConfiguration.class);

    @BeforeEach
    void setUp() {
        DatabasePopulatorUtils.execute(applicationContext.getBean(DataSource.class));
    }

    @Test
    void testChangePassword() {
        AppUserService userService = applicationContext.getBean(AppUserService.class);
        final var newPassword = "qqqqq";
        final var createBy = "gugu";
        userService.changePassword(1L, newPassword, createBy);

        final var actual = userService.findById(1L);

        assertThat(actual.getPassword()).isEqualTo(newPassword);
    }

    @Test
    void testTransactionRollback() {
        FakeUserService userService = applicationContext.getBean(FakeUserService.class);
        final var newPassword = "newPassword";
        final var createBy = "gugu";

        assertThrows(DataAccessException.class,
            () -> userService.changePassword(1L, newPassword, createBy));

        final var actual = userService.findById(1L);

        assertThat(actual.getPassword()).isNotEqualTo(newPassword);
    }
}
