package camp.nextstep.service;

import camp.nextstep.domain.User;
import com.interface21.dao.DataAccessException;
import com.interface21.transaction.PlatformTransactionManager;

public class TxUserService implements UserService {

    private final PlatformTransactionManager transactionManager;
    private final UserService userService;

    public TxUserService(final PlatformTransactionManager transactionManager, final UserService userService) {
        this.transactionManager = transactionManager;
        this.userService = userService;
    }

    @Override
    public User findById(final long id) {
        return userService.findById(id);
    }

    @Override
    public void save(final User user) {
        userService.save(user);
    }

    @Override
    public void changePassword(final long id, final String newPassword, final String createdBy) {
        transactionManager.begin();

        try {
            userService.changePassword(id, newPassword, createdBy);
        } catch (Exception e) {
            transactionManager.rollback();
            throw new DataAccessException(e);
        }

        transactionManager.commit();
    }
}
