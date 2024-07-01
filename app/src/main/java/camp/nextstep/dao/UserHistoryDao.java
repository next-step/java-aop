package camp.nextstep.dao;

import camp.nextstep.domain.UserHistory;
import com.interface21.jdbc.core.JdbcTemplate;
import com.interface21.jdbc.core.KeyHolder;
import com.interface21.jdbc.core.PreparedStatementCreator;
import com.interface21.beans.factory.annotation.Autowired;
import com.interface21.context.stereotype.Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;

@Repository
public class UserHistoryDao {

    private static final Logger log = LoggerFactory.getLogger(UserHistoryDao.class);

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserHistoryDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void log(final UserHistory userHistory) {
        final var sql = String.join(" ",
            "insert into user_history (user_id, account, password, email, created_at, created_by)",
            "values (?, ?, ?, ?, ?, ?)"
        );
        final PreparedStatementCreator psc = con -> {
            final var pstmt = con.prepareStatement(sql);
            pstmt.setLong(1, userHistory.getUserId());
            pstmt.setString(2, userHistory.getAccount());
            pstmt.setString(3, userHistory.getPassword());
            pstmt.setString(4, userHistory.getEmail());
            pstmt.setTimestamp(5, Timestamp.valueOf(userHistory.getCreatedAt()));
            pstmt.setString(6, userHistory.getCreatedBy());
            return pstmt;
        };
        jdbcTemplate.update(psc, new KeyHolder());
    }
}
