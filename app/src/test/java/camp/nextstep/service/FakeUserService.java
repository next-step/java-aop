package camp.nextstep.service;

import camp.nextstep.dao.UserDao;
import camp.nextstep.dao.UserHistoryDao;
import camp.nextstep.domain.User;
import camp.nextstep.domain.UserHistory;
import com.interface21.beans.factory.annotation.Autowired;
import com.interface21.context.stereotype.Service;
import com.interface21.transaction.annotation.Transactional;

@Service
public class FakeUserService {

    private final UserDao userDao;
    private final UserHistoryDao userHistoryDao;

    @Autowired
    public FakeUserService(UserDao userDao) {
        this.userDao = userDao;
        this.userHistoryDao = new StubUserHistoryDao(null);
    }

    public User findByAccount(final String account) {
        return userDao.findByAccount(account);
    }

    public User findById(final long id) {
        return userDao.findById(id);
    }

    public void save(final User user) {
        userDao.insert(user);
    }

    @Transactional
    public void changePassword(final long id, final String newPassword, final String createBy) {
        final var user = findById(id);
        user.changePassword(newPassword);
        userDao.update(user);
        userHistoryDao.log(new UserHistory(user, createBy));
    }
}
