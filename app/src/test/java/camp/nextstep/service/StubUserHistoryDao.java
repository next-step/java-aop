package camp.nextstep.service;

import camp.nextstep.dao.UserHistoryDao;
import camp.nextstep.domain.UserHistory;
import com.interface21.dao.DataAccessException;
import com.interface21.jdbc.core.JdbcTemplate;

public class StubUserHistoryDao extends UserHistoryDao {

    public StubUserHistoryDao(final JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    public void log(final UserHistory userHistory) {
        throw new DataAccessException();
    }
}
