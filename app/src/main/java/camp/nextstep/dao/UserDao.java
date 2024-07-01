package camp.nextstep.dao;

import camp.nextstep.domain.User;
import com.interface21.jdbc.core.JdbcTemplate;
import com.interface21.jdbc.core.KeyHolder;
import com.interface21.jdbc.core.PreparedStatementCreator;
import com.interface21.jdbc.core.RowMapper;
import com.interface21.beans.factory.annotation.Autowired;
import com.interface21.context.stereotype.Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Repository
public class UserDao {

    private static final Logger log = LoggerFactory.getLogger(UserDao.class);

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void insert(final User user) {
        final var sql = "insert into users (account, password, email) values (?, ?, ?)";
        final PreparedStatementCreator psc = con -> {
            final var pstmt = con.prepareStatement(sql);
            pstmt.setString(1, user.getAccount());
            pstmt.setString(2, user.getPassword());
            pstmt.setString(3, user.getEmail());
            return pstmt;
        };
        jdbcTemplate.update(psc, new KeyHolder());
    }

    public void update(final User user) {
        final var sql = "update users set account = ?, password = ?, email = ? where id = ?";
        jdbcTemplate.update(sql, user.getAccount(), user.getPassword(), user.getEmail(), user.getId());
    }

    public List<User> findAll() {
        final var sql = "select id, account, password, email from users";
        final RowMapper<User> rm = getUserRowMapper();
        return jdbcTemplate.query(sql, rm);
    }

    public User findById(final Long id) {
        final var sql = "select id, account, password, email from users where id = ?";
        return jdbcTemplate.queryForObject(sql, getUserRowMapper(), id);
    }

    public User findByAccount(final String account) {
        final var sql = "select id, account, password, email from users where account = ?";
        return jdbcTemplate.queryForObject(sql, getUserRowMapper(), account);
    }

    private static RowMapper<User> getUserRowMapper() {
        return rs -> new User(
            rs.getLong(1),
            rs.getString(2),
            rs.getString(3),
            rs.getString(4)
        );
    }
}
