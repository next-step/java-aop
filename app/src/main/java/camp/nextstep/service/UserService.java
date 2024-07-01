package camp.nextstep.service;

import camp.nextstep.domain.User;

import java.sql.SQLException;

public interface UserService {

    User findById(final long id);
    void save(final User user);
    void changePassword(final long id, final String newPassword, final String createdBy) throws SQLException;
}
